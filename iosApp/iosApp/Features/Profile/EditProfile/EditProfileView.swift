//
//  EditProfileView.swift
//  iosApp
//

import Shared
import SwiftUI

struct EditProfileView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: UserViewModel = KoinHelper().getUserViewModel()

    @State private var userState: UserState = UserState.Idle()
    @State private var updateState: UpdateProfileState = UpdateProfileState.Idle()
    @State private var showError = false
    @State private var errorMsg = ""

    @State private var fullname = ""
    @State private var username = ""
    @State private var email = ""
    @State private var phoneNumber = ""
    @State private var gender = ""
    @State private var birthDate = ""
    @State private var populated = false

    private var isLoading: Bool { updateState is UpdateProfileState.Loading }

    var body: some View {
        Form {
            Section("Personal Info") {
                LabeledField("Full name", text: $fullname)
                LabeledField("Username", text: $username)
                LabeledField("Email", text: $email).keyboardType(.emailAddress)
                LabeledField("Phone number", text: $phoneNumber).keyboardType(.phonePad)
            }
            Section("Additional") {
                LabeledField("Gender (male/female)", text: $gender)
                LabeledField("Birth date (DD-MM-YYYY)", text: $birthDate)
            }
            Section {
                Button(action: submit) {
                    if isLoading {
                        HStack { ProgressView(); Text("Saving...") }
                    } else {
                        Text("Save Changes")
                            .frame(maxWidth: .infinity)
                            .fontWeight(.semibold)
                    }
                }
                .disabled(isLoading)
            }
        }
        .navigationTitle("Edit Profile")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button("Cancel") { rootView = .main }
            }
        }
        .alert("Error", isPresented: $showError) {
            Button("OK") {}
        } message: { Text(errorMsg) }
        .onAppear { observeStates(); viewModel.getUserLogin() }
    }

    private func submit() {
        viewModel.updateProfile(params: UpdateProfileParamsEntity(
            fullname: fullname.isEmpty ? nil : fullname,
            username: username.isEmpty ? nil : username,
            email: email.isEmpty ? nil : email,
            phoneNumber: phoneNumber.isEmpty ? nil : phoneNumber,
            gender: gender.isEmpty ? nil : gender,
            birthDate: birthDate.isEmpty ? nil : birthDate,
            language: nil,
            currency: nil
        ))
    }

    private func observeStates() {
        viewModel.userState.subscribe { s in
            DispatchQueue.main.async {
                if let s = s {
                    self.userState = s
                    if !self.populated, let success = s as? UserState.Success {
                        let u = success.data
                        self.fullname = u.fullname ?? ""
                        self.username = u.username ?? ""
                        self.email = u.email ?? ""
                        self.phoneNumber = u.phoneNumber ?? ""
                        self.gender = u.gender ?? ""
                        self.birthDate = u.birthDate ?? ""
                        self.populated = true
                    }
                }
            }
        }
        viewModel.updateProfileState.subscribe { s in
            DispatchQueue.main.async {
                if let s = s {
                    self.updateState = s
                    if s is UpdateProfileState.Success {
                        viewModel.resetUpdateProfileState()
                        self.rootView = .main
                    } else if let e = s as? UpdateProfileState.Error {
                        self.errorMsg = e.message
                        self.showError = true
                        viewModel.resetUpdateProfileState()
                    }
                }
            }
        }
    }
}

private struct LabeledField: View {
    let title: String
    @Binding var text: String
    init(_ title: String, text: Binding<String>) {
        self.title = title
        self._text = text
    }
    var body: some View {
        TextField(title, text: $text)
    }
    func keyboardType(_ type: UIKeyboardType) -> some View {
        self.modifier(KeyboardModifier(type: type))
    }
}

private struct KeyboardModifier: ViewModifier {
    let type: UIKeyboardType
    func body(content: Content) -> some View {
        content
    }
}
