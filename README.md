# GPSWorksApp
O aplicativo usa um conjunto de bibliotecas do Android Jetpack mais Retrofit para exibir dados da API REST. O aplicativo usa Kotlin.

### Prerequisites
O projeto tem todas as dependências necessárias nos arquivos gradle. Adicione o projeto ao Android Studio ou Intelij e construa. Todas as dependências necessárias serão baixadas e instaladas.

## Arquitetura
O projeto usa o padrão de arquitetura MVVM

## Bibliotecas

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Gerencia dados relacionados à interface do usuário de maneira ciente do ciclo de vida e atue como um canal entre os casos de uso e a interface do usuário.
* [ViewBinding](https://developer.android.com/topic/libraries/data-binding) - Biblioteca de suporte que permite vincular componentes de interface do usuário em layouts a fontes de dados, vincula detalhes de caracteres e resultados de pesquisa à interface do usuário.
* [Koin](https://insert-koin.io/) - Para injeção de dependência.
* [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=in) - Permitir a paginação dos dados.
* [Retrofit](https://square.github.io/retrofit/) - Para acessar a API Rest
* [Coroutines](https://developer.android.com/kotlin/coroutines) - Para fazer chamadas assíncrona.
* [Lottie](https://airbnb.design/lottie/) - Para a crianção de animações.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - Para a criação de banco local.


