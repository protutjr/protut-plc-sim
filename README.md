# Simulador de CLP Modbus da Protut

### Sobre este software
Este software simula a comunicação com um dispositivo Modbus TCP. Desenvolvido para a Empresa Júnior [Protut](https://protut.com.br), este é um WebApp simples para o Apache Tomcat que pode rodar lado a lado com outras aplicações (tipicamente o ScadaBR).

Através de uma página HTML, o usuário terá acesso a uma interface didática que imita um CLP de pequeno porte. As entradas e saídas digitais desse "CLP virtual" são associadas a endereços Modbus TCP. A cada atualização dos dados, o simulador anima as informações do CLP, tornando o aprendizado muito mais intuitivo e dinâmico.

### Instalação
- Baixe o arquivo [clp.war](https://github.com/protutjr/protut-plc-sim/releases/download/v1.0.1/clp.war)
- Copie o arquivo para dentro da pasta de instalação do seu Tomcat/ScadaBR (no Windows, copie para dentro de `C:\Program Files\ScadaBR\tomcat\webapps\`)
- Reinicie o serviço do Tomcat/ScadaBR (ou reinicie o computador)
- Na próxima vez em que for iniciado, o serviço do Tomcat/ScadaBR irá carregar automaticamente o novo WebApp


### Configuração
Este software tem finalidade demonstrativa e didática, possuindo poucos recursos de personalização. Você pode configurar, principalmente, os parâmetros da comunicação Modbus. Para isso, acesse a pasta `WEB-INF` em seu WebApp e edite o arquivo `application.properties`.

A configuração padrão do arquivo `application.properties` é a seguinte:

~~~properties
tcp.port=502
slave.id=2
use.keep.alive=true
~~~

Obs.: Não adicione comentários ao arquivo `application.properties` ou a aplicação não irá funcionar corretamente.


### Licença
Este é um software open source, licenciado sob a licença Apache 2.0.
