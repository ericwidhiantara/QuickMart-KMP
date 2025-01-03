import SwiftUI

struct BottomNavigationItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
    let iconActive: String
    let view: AnyView
}

class BottomNavViewModel: ObservableObject {
    @Published var currentIndex: Int = 0

    func updateIndex(_ index: Int) {
        currentIndex = index
    }
}

struct BottomNavBar: View {
    @Binding var rootView: AppScreen

    @StateObject private var viewModel = BottomNavViewModel()

    let navbarItems: [BottomNavigationItem]
    init(rootView: Binding<AppScreen>) {
        _rootView = rootView
        navbarItems = [
            BottomNavigationItem(
                title: "menu_home",
                icon: "MenuHome",
                iconActive: "MenuHomeActive",
                view: AnyView(HomeScreen())
            ),
            BottomNavigationItem(
                title: "menu_categories",
                icon: "MenuCategories",
                iconActive: "MenuCategoriesActive",
                view: AnyView(CategoryView(rootView: rootView))
            ),
            BottomNavigationItem(
                title: "menu_my_cart",
                icon: "MenuMyCart",
                iconActive: "MenuMyCartActive",
                view: AnyView(MyCartView(rootView: rootView))
            ),
            BottomNavigationItem(
                title: "menu_wishlist",
                icon: "MenuWishlist",
                iconActive: "MenuWishlistActive",
                view: AnyView(MyWishlistView(rootView: rootView))
            ),
            BottomNavigationItem(
                title: "menu_profile",
                icon: "MenuProfile",
                iconActive: "MenuProfileActive",
                view: AnyView(ProfileScreen(rootView: rootView))
            ),
        ]
    }

    var body: some View {
        TabView(selection: $viewModel.currentIndex) {
            ForEach(Array(navbarItems.enumerated()), id: \.element.id) {
                index, item in
                item.view
                    .tabItem {
                        Label(
                            NSLocalizedString(item.title, comment: ""),
                            image: viewModel.currentIndex == index
                                ? item.iconActive : item.icon
                        )
                    }
                    .tag(index)
            }
        }
        .tint(.colorCyan)
    }
}

struct HomeScreen: View {
    var body: some View {
        Text("Home Screen")
    }
}

struct CategoryListScreen: View {
    var body: some View {
        Text("Category Screen")
    }
}
