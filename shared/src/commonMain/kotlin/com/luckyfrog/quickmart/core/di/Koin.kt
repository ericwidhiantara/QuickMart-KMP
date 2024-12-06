package com.luckyfrog.quickmart.core.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            ktorModule,
            apiModule,
            dataSourceModule,
            repositoryModule,
            dispatcherModule,
            useCaseModule,
            platformViewModelModule(),
            platformAppModule(),
        )
    }


//val viewModelModule = module {
//    factory { CharactersViewModel(get()) }
//    factory { CharactersFavoritesViewModel(get()) }
//    factory { params -> CharacterDetailViewModel(get(), get(), get(), params.get()) }
//}
//
//val useCasesModule: Module = module {
//    factory { GetCharactersUseCase(get(), get()) }
//    factory { GetCharactersFavoritesUseCase(get(), get()) }
//    factory { GetCharacterUseCase(get(), get()) }
//    factory { IsCharacterFavoriteUseCase(get(), get()) }
//    factory { SwitchCharacterFavoriteUseCase(get(), get()) }
//}
//
//val repositoryModule = module {
//    single<IRepository> { RepositoryImp(get(), get()) }
//    single<ICacheData> { CacheDataImp(get()) }
//    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }
//}

//
//val sqlDelightModule = module {
//    single { SharedDatabase(get()) }
//}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}
//
//val mapperModule = module {
//    factory { ApiCharacterMapper() }
//}
