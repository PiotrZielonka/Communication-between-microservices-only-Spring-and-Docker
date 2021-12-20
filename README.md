1. Docker musi być włączony

2. Lokalnie do uruchomienia testów mikro serwisy potrzebują połączenia z bazą danych PostgreSQL pod portem 5432 i bazę danych o nazwie taskForTest  dlatego jeśli nie ma tego połączenia istnieje potrzeba uruchomienia komendy mvnw clean install -DskipTests zamiast mvnw clean install w katalogu Piotr Zielonka task która spowoduje automatyczne utworzenie obrazów dockerowych do wszystkich mikro serwisów i Javy 11 komenda ta generuje również niezbędne pliki dto Mapstructa.

3. Komenda docker-compose up wykonana w katalogu Piotr Zielonka task uruchomi wszystkie mikro serwisy bazę danych PostrgeSQL i narzędzie PgAdmin w środowisku dockera na odpowiednich portach

4. Pod portem 5050 należy skonfigurowania połączenie z bazą danych w narzędziu PgAdmin które uruchomił wcześniej docker z pliku docker-compose

a) logowanie do pgAdmin email: pgadmin4@pgadmin.org hasło admin

b) Servers > Create > Server

c) W zakładce General pole Name dowolna nazwa

d) W zakładce Connection pole Hostname/address port naszego dockera np 192.168.99.100 lub localhost

f) Port 5432

g) Maintenance database postgres

h) Username postgres

i) Password slon

j) Password slon normalnie slon bez polskich znaków

k) Save

Powinna być baza danych o nazwie task z dostępnymi trzema tabelami credit customer i product jeśli ich nie ma na pewno będą po ponownym uruchomieniu powyższych kroków nie będziemy wtedy musieli postawić serwera bazy danych można też je dodać teraz wpisując polecenie SQL które jest w mikroserwisie Credit src/main/resources schema.sql robimy kopiuj wklej i tworzymy 3 tabele

5. Poniżej poprawny plik JSON do utworzenia kredytu do bazy danych w Postmanie uderzamy nim pod adres localhost:8080/credits metodą POST w przypadku błędnego wpisania jakiegoś pola wyskoczy błąd walidacji

{

  "creditName": "The cash loan",

   "customerDto": {

     "firstName": "Alice",

     "surname": "Nowak",

     "pesel": "12345678901"

   },

    "productDto": {

     "name": "Ecologic",

     "productValue": 12345

   }

}


6. Pod adresem localhost:8080/credits możemy pobrać teraz metodą GET kredyty które przed chwilą wprowadziliśmy

7. Jeśli w środowisku dockera nie działa poprawnie pobranie lub utworzenie kredytu najprawdopodobniej jest to spowodowane tym że docker nie akceptuje nazwy localhost w adresie url endpointów wtedy należy zmienić nazwę locacalhost na swój port dockera w metodach saveProductToProductMicroserviceThereIsProductTableLogic i saveCustomerToCustomerMicroserviceThereIsCustomerTableLogic w mikroserwisie Credit w klasie CreditController i w klasie CreditServiceImpl w metodach getProductDtoByCreditIdFromProductMicroservice i getCustomerDtoByCreditIdFromCustomerMicroservice również w microserwisie Credit