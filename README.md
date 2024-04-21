# HEPH - Short for Hephaestus (The Forger of Metal)

* `initialize/init` - Initialize a new HEPH home cabinet.
  * ✓ If one does not exist in the user home directory it gets created
  * If one exists, it can be overwritten with a `--force` flag or relocated with a `--path` flag 
* `show-config` - Show the current top level configuration
* `list/ls  [cab-id]:[shelf-id]:[template-id] -verbose ` - List all attached cabinet ids/shelf ids/template ids
  * if no cab id is provided, list all cabinet ids
  * if cab id is provided, but no shelf id is provided, list all shelf ids in the cabinet
  * if cab id and shelf id is provided, list all entry ids in the shelf
* `set-cab <cab-id> --path <path to local or remote folder> --description <Description of the cabinet>` - a new cabinet (remote or local) to the set of cabinets
  * If the cab id already exists, it can be overwritten or modified using a `--force` flag
  * If the cab id does not exist, it is created and the sub folders are imported as shelves
  * If the cab id already exists but the path is modified, the new sub folders are imported as shelves 
* `unset-cab <cab-id>` - Remove a cabinet (remote or local) from the set of cabinets
* `set-shelf <cab-id>:<shelf-id> --description <desc> --path <path-within-cab>` - Create a shelf with the given id and point it to given path
  * If the shelf id exists and you are supplying a new path you need to use a `--force` flag  
* `unset-shelf <cab-id>:<shelf-id>`  - Remove a shelf from a given cabinet
* `set-template <cab-id>:<shelf-id>:<entry-id> --path <path-within-shelf> --description <desc>` - Specify a file or a folder within a shelf as an entry 
* `unset-template <cab-id>:<shelf-id>`  

* `model`
  * `import <model-id> --path <path-to-file>` - Initialize a new stored model from a file
  * `set <model-id> <key> <value>` - Set a key value pair in a model
  * `get <model-id> <key>` - Get the value of a key in a model
  * `list [<model-id>]` - List all model ids or all key value pairs in a model

* `generate`
  * `--real` - Generate for real rather than dry run
  * `--source-folder <path-to-local-folder>` - Source folder to be treated as a shelf in a cabinet
  * `--source <cab-d>:<shelf-id>:<template-id>` - Template to be used as a source
  * `--destination [<path-to-local-folder>]` - Destination folder to be generated to
  * `-clip/--clipboard` - Copy generated content to clipboard
  * `--model <model-id>` - Model to be used for generation


## Data Model

Cabinet -> Shelf -> Template

Cabinet
  - id
  - description
  - path
  - shelves
- Shelf
  - id
  - description
  - path
  - templates
- Template
  - id
  - description
  - path

Model
  - id
  - key-value pairs

Snippet
    - id
    - description
    - path


## Template Language

## Cabinet
A cabinet is a collection of shelves. The default Heph home cabinet can be found in the user home directory. 
A shelf is a single folder or file in the root of the cabinet.    
A cabinet can be local or remote. A shelf can be imported from a local or remote folder into the home cabinet. 

## Shelf




## Micronaut 4.2.2 Documentation

- [User Guide](https://docs.micronaut.io/4.2.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.2.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.2.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Rewrite Gradle Plugin](https://plugins.gradle.org/plugin/org.openrewrite.rewrite)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
## Feature mockito documentation

- [https://site.mockito.org](https://site.mockito.org)


## Feature validation documentation

- [Micronaut Validation documentation](https://micronaut-projects.github.io/micronaut-validation/latest/guide/)


## Feature lombok documentation

- [Micronaut Project Lombok documentation](https://docs.micronaut.io/latest/guide/index.html#lombok)

- [https://projectlombok.org/features/all](https://projectlombok.org/features/all)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature openrewrite documentation

- [https://docs.openrewrite.org/](https://docs.openrewrite.org/)


