import Shared
import SwiftUI
import UIKit

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
