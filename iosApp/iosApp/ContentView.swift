import SwiftUI
import Shared

struct ContentView: View {
    @StateObject private var viewModel = IOSExpenseViewModel()
    @State private var personName = ""
    @State private var expenseAmount = ""
    @State private var selectedPersonIndex = 0

    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("Add Person")) {
                    TextField("Person Name", text: $personName)
                    Button("Add Person") {
                        guard !personName.trimmingCharacters(in: .whitespaces).isEmpty else { return }
                        viewModel.addPerson(name: personName.trimmingCharacters(in: .whitespaces))
                        personName = ""
                    }
                }

                if !viewModel.persons.isEmpty {
                    Section(header: Text("People")) {
                        ForEach(Array(viewModel.persons.enumerated()), id: \.offset) { _, person in
                            Text(person.name)
                        }
                    }
                }

                Section(header: Text("Add Expense")) {
                    TextField("Amount", text: $expenseAmount)
                        .keyboardType(.decimalPad)
                    if !viewModel.persons.isEmpty {
                        Picker("Paid By", selection: $selectedPersonIndex) {
                            ForEach(0..<viewModel.persons.count, id: \.self) { index in
                                Text(viewModel.persons[index].name).tag(index)
                            }
                        }
                    }
                    Button("Add Expense") {
                        guard let amount = Double(expenseAmount),
                              !viewModel.persons.isEmpty else { return }
                        let paidById = viewModel.persons[selectedPersonIndex].id
                        viewModel.addExpense(amount: amount, paidById: paidById)
                        expenseAmount = ""
                    }
                }

                if !viewModel.expenses.isEmpty {
                    Section(header: Text("Expenses")) {
                        ForEach(Array(viewModel.expenses.enumerated()), id: \.offset) { _, expense in
                            let paidByName = viewModel.persons.first(where: { $0.id == expense.paidBy })?.name ?? expense.paidBy
                            Text("\(paidByName) paid ₹\(expense.amount, specifier: "%.2f")")
                        }
                    }
                }

                Section {
                    Button("Calculate Settlements") {
                        viewModel.calculate()
                    }
                }

                if !viewModel.settlements.isEmpty {
                    Section(header: Text("Settlements")) {
                        ForEach(Array(viewModel.settlements.enumerated()), id: \.offset) { _, settlement in
                            let fromName = viewModel.persons.first(where: { $0.id == settlement.from })?.name ?? settlement.from
                            let toName = viewModel.persons.first(where: { $0.id == settlement.to })?.name ?? settlement.to
                            Text("\(fromName) owes \(toName) ₹\(settlement.amount, specifier: "%.2f")")
                        }
                    }
                }
            }
            .navigationTitle("Expense Splitter")
        }
    }
}

class IOSExpenseViewModel: ObservableObject {
    @Published var persons: [Person] = []
    @Published var expenses: [Expense] = []
    @Published var settlements: [Settlements] = []

    private let useCase = CalculateExpenseUseCase()

    func addPerson(name: String) {
        let person = Person(id: NSUUID().uuidString, name: name)
        persons.append(person)
    }

    func addExpense(amount: Double, paidById: String) {
        guard !persons.isEmpty else { return }
        let splitAmount = amount / Double(persons.count)
        let splits = persons.map { Split(personId: $0.id, amount: splitAmount) }
        let expense = Expense(
            id: NSUUID().uuidString,
            amount: amount,
            paidBy: paidById,
            splits: splits
        )
        expenses.append(expense)
    }

    func calculate() {
        let result = useCase.execute(expenses: expenses, persons: persons)
        settlements = result.settlements.compactMap { $0 as? Settlements }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

