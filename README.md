# Laer
![GitHub CI](https://github.com/Papagaios-Estocasticos/laer/actions/workflows/deploy.yml/badge.svg)

## Descrição

Aplicação de consolidação de dados para ativos listados na B3. As coletas serão feitas via web-scraping de maneira responsavel, i.e, sem sobrecarregar os sites que hospedam os dados.
Os ativos suportados são:
- Títulos do Tesouro, CDBs, CRIs, LCIs, CRAs, LCAs, FIIs e FIAGROs.

Essa aplicação não tem interfaces para obtenção dos dados, ela será apenas uma automação para carga dos dados em um banco de dados privado. 
O banco será utilizado para construção de relatórios e agregações, a manutenção dessa base deve ocorrer exclusivamente pela aplicação inclusive para correções manuais de dados.
