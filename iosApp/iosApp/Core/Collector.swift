//
//  Collector.swift
//  iosApp
//
//  Created by Eric on 12/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import Foundation
import Combine
import shared

// Helper extension to convert Kotlin Flow to Swift Combine publisher
class Collector<T>: Kotlinx_coroutines_coreFlowCollector {
    private let handler: (T) -> Void
    
    init(handler: @escaping (T) -> Void) {
        self.handler = handler
    }
    
    func emit(value: T, completionHandler: @escaping (Error?) -> Void) {
        handler(value)
        completionHandler(nil)
    }
}
