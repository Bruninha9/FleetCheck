# FleetCheck – Build Systems Lab

This project is intentionally incomplete. Follow the worksheet in the order given.

Expected final application output:

```
FleetCheck 1.0
Vehicles loaded: 4
Vehicles requiring service: 2
Average mileage: 37000 km
```

Do not copy the solution POM. The objective is to observe how each build change alters the result.

## Respostas às Evidências (Maven)

### Evidence 1: Erro Inicial de Compilação
- **Linha do Erro:** `[ERROR] /src/main/java/pt/upt/fleetcheck/App.java:[3,32] cannot find symbol class ObjectMapper`
- **Causa:** O ficheiro `App.java` tenta importar a classe `com.fasterxml.jackson.databind.ObjectMapper`, mas a biblioteca do Jackson ainda não estava declarada no `pom.xml`.

### Evidence 4: Mudança Introduzida pelo Maven Shade Plugin
- **Análise do JAR:** O comando padrão `mvn package` gera um JAR simples contendo apenas as classes do próprio projeto, sem as dependências externas.
- **Transformação:** O `maven-shade-plugin` extrai e empacota todo o bytecode das dependências (ex: Jackson) juntamente com o código da aplicação no ficheiro `fleetcheck-1.0.0-all.jar`, além de configurar o cabeçalho `Main-Class` no manifesto. Isso transforma o artefacto num **FAT JAR** totalmente autónomo e executável via `java -jar`.

### Evidence 7: Componentes Não Declarados no SBOM
- **Razão:** O plugin CycloneDX analisa a **árvore completa de dependências resolvidas** do projeto. Como a dependência direta `jackson-databind` possui dependências transitivas (`jackson-core` e `jackson-annotations`), todas elas são incluídas no `bom.json` para garantir a rastreabilidade total da cadeia de fornecimento de software (software supply chain).