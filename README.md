# Simulador de CLP Modbus da Protut

![Screenshot da tela principal](https://github.com/protutjr/protut-plc-sim/blob/ae2d9e481ea683030d0a864d0082f5b544940bf3/Screenshot.png)

### Sobre este software
Este software simula os recursos de comunicação de um dispositivo Modbus TCP. Desenvolvido para a Empresa Júnior [Protut](https://protut.com.br), este é um WebApp simples para o Apache Tomcat que pode rodar lado a lado com outras aplicações (tipicamente o ScadaBR).

Através de uma página HTML, o usuário terá acesso a uma interface didática que imita um CLP de pequeno porte. As entradas e saídas digitais desse CLP virtual são associadas a endereços Modbus TCP. A cada atualização dos dados, o simulador anima as informações do CLP, tornando o aprendizado muito mais intuitivo e dinâmico.

### Instalação
- Baixe o arquivo [clp.war](https://github.com/protutjr/protut-plc-sim/releases/download/v1.0.0/clp.war)
- Copie o arquivo para dentro da pasta de instalação do seu Tomcat/ScadaBR (no Windows, copie para dentro de `C:\Program Files\ScadaBR\tomcat\webapps\`)
- Reinicie o serviço do Tomcat/ScadaBR (ou reinicie o computador)
- Na próxima vez em que for iniciado, o serviço do Tomcat/ScadaBR irá carregar automaticamente o novo WebApp

### Créditos
Desenvolvido por Celso D. A. Ubaldo, para a empresa júnior [Protut](https://protut.com.br).

### Licença
Este é um software open source, licenciado sob a licença Apache 2.0.
