# Simulador de CLP Modbus da Protut

![Screenshot da tela principal](https://raw.githubusercontent.com/protutjr/protut-plc-sim/main/Screenshot.png?token=GHSAT0AAAAAAB6KVF46GK3M26ZQESK7AOPCZDY3CPA)

### Sobre este software
Este software simula os recursos de comunicação de um dispositivo Modbus TCP. Desenvolvido para a Empresa Júnior [Protut](www.protut.com.br), este é um WebApp simples para o Apache Tomcat que pode rodar lado a lado com outras aplicações (tipicamente o ScadaBR).

Através de uma página HTML, o usuário terá acesso a uma interface didática que imita um CLP de pequeno porte. As entradas e saídas digitais desse CLP virtual são associadas a endereços Modbus TCP. A cada atualização dos dados, o simulador anima as informações do CLP, tornando o aprendizado muito mais intuitivo e dinâmico.

### Instalação
- Baixe o arquivo **.war**
- Copie o arquivo para dentro da pasta de instalação do seu Tomcat/ScadaBR (no Windows, copiar para dentro de `C:\Program Files\ScadaBR\tomcat\webapps\`)
- Reinicie o serviço do Tomcat/ScadaBR (ou reinicie o computador)
- Na próxima vez em que for iniciado, o serviço do Tomcat/ScadaBR irá carregar automaticamente o novo WebApp

### Créditos
Desenvolvido por Celso D. A. Ubaldo, para a empresa júnior [Protut](www.protut.com.br).

### Licença
Este é um software open source, licenciado sob a licença Apache 2.0.
