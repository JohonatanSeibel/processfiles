# ProcessFiles

O sistema tem a finalidade de ler um lote de arquivos com uma estrutura pré-definida no diretório:  **"%HOMEPATH%/data/in"** e calcular os resultados pretendidos e criar um arquivo de retorno no diretório: " **"%HOMEPATH%/data/out"**. Estes arquivos tem que obrigatoriamente estar no formato **".dat"**, caso contrário, vai ser ignorado pela aplicação. A análise só vai ser concluída assim que todos os arquivos do diretório forem processados. E enquanto a aplicação estiver iniciada, qualquer alteração no diretório irá fazer uma nova análise dos arquivos.


#### Dados do vendedor
Os dados do vendedor têm o formato **id 001** e a linha terá o seguinte formato: **001çCPFçNameçSalary**

#### Dados do Cliente
Os dados do cliente têm o formato id **002** e a linha terá o seguinte formato: **002çCNPJçNameçBusiness Area**

#### Dados de vendas
Os dados de vendas têm o formato **id 003**. Dentro da linha de vendas, existe a lista de itens, que é envolto por colchetes []. A linha terá o seguinte formato: **003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name**

#### Modelo de arquivo de Exemplo
O seguinte é um exemplo dos dados que o sistema deve ser capaz de ler.
>*001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo*

**Obs.:**  *Não é preciso ter necessariamente dados do Vendedor, Cliente e Venda no mesmo arquivo. Podem estar em arquivos separados.*

## Especificações necessárias de ambiente:

 - Java 8 ou superior
 - Maven 3.0.5 ou superior

## Como rodar exemplo para teste em um container Docker:

1º - Passo: Abrir o terminal e navegar até o diretório do projeto dentro do Worksapace;
2º - Passo: Executar o comando abaixo para buildar a aplicação;
    
    $ mvn package -Dmaven.test.skip=true

3º - Passo: Ainda dentro do diretório do projeto execute o comando abaixo para cria o container;

    $ docker build -t processfiles:v1.0.0 . --force-rm

4º - Passo: Rodar o container;

    $ docker run -d -i -t \
        -v $HOMEPATH/data/in:/root/data/in -w /root/data/in \
        -v $HOMEPATH/data/out:/root/data/out -w /root/data/out \
        --name=processfiles \
        processfiles:v1.0.0

 
