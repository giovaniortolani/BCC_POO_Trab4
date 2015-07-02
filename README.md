Supermercado
======
#### O que é?
Supermercado é um software em Java que simula um supermercado online. Possui aplicações para servidor e cliente.
Confira a descrição do trabalho no link: https://docs.google.com/document/d/1hZco9xbu2Q6F3rICbgalWUufmuWPvREZUmELk1MDTDs/edit

#### Contribuidores
Julia Minetto Macias 8937329
Luiz Augusto Vieira Manoel 8937308
Giovani Ortolani Barbosa 8936648

#### Como compilar?
- O projeto foi feito no Eclipse.
- Para rodar o servidor:
	 Execute o ServerConection.java que se encontra na pasta src;
- Para rodar o cliente:
	 Execute o MercadoClient.java que se encontra na pasta src;
OBS: É possivel executar varios clientes ao mesmo tempo, basta rodar o MercadoClient.java que gera uma nova conexão.

#### Design Pattern
Utilizamos dois padroes de projetos criacionais:
- Singleton Pattern:
	Como só pode haver uma instancia que faça a conexao entre o servidos e os cliente utilizamos este projeto como garantia para resolver este projeto (ServerAndClient)
- Factory Method:
	Utilizamos este padrão de projeto para gerar uma abstração ao instanciar um novo Cliente(ClientConection).
