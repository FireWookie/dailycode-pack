
import SwiftUI
import Shared

struct ContentView: View {
    init() {
        _ = App()
    }
    var body: some View {
        ZStack {
            Text(
                "Hello!"
            )
        }
        .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



