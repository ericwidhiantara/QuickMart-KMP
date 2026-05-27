import SwiftUI
import Shared

struct BottomNavigationItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
    let iconActive: String
    let view: AnyView
}

// Separate view for tab item
struct TabItemView: View {
    let item: BottomNavigationItem
    let isSelected: Bool

    var body: some View {
        item.view
            .tabItem {
                Label {
                    Text(NSLocalizedString(item.title, comment: ""))
                } icon: {
                    Image(isSelected ? item.iconActive : item.icon)
                }
            }
            .tag(isSelected ? 0 : 1) // Ensure consistent tagging
    }
}


struct BottomNavBar: View {
    @Binding var rootView: AppScreen
    @StateObject private var viewModel: NavBarViewModel = KoinHelper().getNavBarViewModel()
    private let navbarItems: [BottomNavigationItem]

    init(rootView: Binding<AppScreen>) {
        _rootView = rootView
        self.navbarItems = [
            BottomNavigationItem(
                title: "menu_home",
                icon: "MenuHome",
                iconActive: "MenuHomeActive",
                view: AnyView(HomeView(rootView: rootView))
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
            )
        ]
    }

    private var selection: Binding<Int> {
        Binding(
            get: {
                Int(truncating: viewModel.currentIndex.value ?? 0)
            },
            set: { newValue in
                viewModel.updateIndex(index: Int32(newValue))
            }
        )
    }


    var body: some View {
        TabView(selection: selection) {
            ForEach(0..<navbarItems.count, id: \.self) { index in
                TabItemView(
                    item: navbarItems[index],
                    isSelected: Int(truncating: viewModel.currentIndex.value ?? 0) == index
                )
                .tag(index)
            }
        }
        .tint(.colorCyan)
    }

}
