//
//  Carousel.swift
//  iosApp
//
//  Created by Eric on 06/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI

struct CarouselData {
    let imageUrl: String
    let title: String
    let subtitle: String
}

struct Carousel: View {
    @State private var currentPage = 0
    
    let items = [
        CarouselData(
            imageUrl: "https://via.placeholder.com/348x148",
            title: "On Headphones",
            subtitle: "Exclusive Sales"
        ),
        CarouselData(
            imageUrl: "https://via.placeholder.com/348x148",
            title: "Title 2",
            subtitle: "Subtitle 2"
        ),
        CarouselData(
            imageUrl: "https://via.placeholder.com/348x148",
            title: "Title 3",
            subtitle: "Subtitle 3"
        )
    ]
    
    // Timer for auto-scrolling
    let timer = Timer.publish(every: 5, on: .main, in: .common).autoconnect()
    
    var body: some View {
        TabView(selection: $currentPage) {
            ForEach(0..<items.count, id: \.self) { index in
                CarouselItemWithOverlay(
                    item: items[index],
                    arraySize: items.count,
                    currentIndex: index
                )
            }
        }
        .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
        .frame(height: 148) // Fixed height as in original
        .onReceive(timer) { _ in
            withAnimation {
                currentPage = (currentPage + 1) % items.count
            }
        }
    }
}

struct CarouselItemWithOverlay: View {
    let item: CarouselData
    let arraySize: Int
    let currentIndex: Int
    
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                // Background Image
                AsyncImage(url: URL(string: item.imageUrl)) { phase in
                    switch phase {
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                    default:
                        Color.gray
                    }
                }
                
                // Overlay content
                VStack(alignment: .leading, spacing: 6) {
                    Spacer()
                    
                    // Discount Tag
                    Text("30% OFF")
                        .font(.system(size: 10, weight: .bold))
                        .foregroundColor(.white)
                        .padding(6)
                        .background(Color.black.opacity(0.7))
                        .cornerRadius(8)
                    
                    // Subtitle
                    Text(item.subtitle)
                        .font(.system(size: 12))
                        .foregroundColor(.white)
                    
                    // Title and Page Indicator
                    HStack {
                        Text(item.title)
                            .font(.system(size: 24, weight: .bold))
                            .foregroundColor(.white)
                        
                        Spacer()
                        
                        // Page Indicator
                        HStack(spacing: 4) {
                            ForEach(0..<arraySize, id: \.self) { index in
                                Circle()
                                    .fill(index == currentIndex ? Color.white : Color.white.opacity(0.5))
                                    .frame(width: 6, height: 6)
                            }
                        }
                    }
                }
                .padding(.horizontal, 28)
                .padding(.bottom, 16)
            }
        }
        .clipShape(RoundedRectangle(cornerRadius: 24))
        .frame(height: 148)
    }
}

struct Carousel_Previews: PreviewProvider {
    static var previews: some View {
        Carousel()
            .padding()
    }
}
