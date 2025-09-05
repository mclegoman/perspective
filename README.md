# Perspective 2.0  
This repository is a work in progress.  

Perspective 2.0 will split features into their own sub-mods.  
This allows us to update features that don't require significant changes quickly, and would allow us to also upload them individually - for those who only want zoom for example.

Splitting perspective into sub-mods, also lets us make things more generalized for other mods to take advantage of.


##
| ID                                          | Name                  | Features                                      | Includes                                                | Progress |
|---------------------------------------------|-----------------------|-----------------------------------------------|---------------------------------------------------------|----------|
| `perspective-base`                          | P:Base                | Abstract/Generic classes, Utils               | Config                                                  | 🚧       |
| `perspective-zoom`                          | Lens                  | Zoom                                          | Base, Config                                            | ❌        |
| `perspective-shaders`                       | Radience              | Shaders                                       | Base, Shader Packs, Kaleidoscope, Super Secret Settings | ❌        |
| `perspective-shaders-shader-packs`          | P:Shader Packs        | Shader Packs                                  | Base, Config, Resources                                 | ❌        |
| `perspective-shaders-kaleidoscope`          | P:Kaleidoscope        | Kaleidoscope                                  | Base, Config, Shader Packs, Luminance                   | ❌        |
| `perspective-shaders-super-secret-settings` | P:SuperSecretSettings | Super Secret Settings                         | Base, Config, Shader Packs, Luminance                   | ❌        |
| `perspective-textured-entity`               | Textured Entity       | Textured Entity                               | Base, Config, Resources                                 | ❌        |
| `perspective-config`                        | P:Config              | Config, Config Screen                         | Base, UI                                                | 🚧       |
| `perspective-resources`                     | P:Resources           | Resource Packs                                | Base                                                    | ❌        |
| `perspective-contributors`                  | P:Contributors        | Contributor Features (inc w/ everything)      | Base                                                    | ❌        |
| `perspective-overlays`                      | P:Overlays            | Overlays                                      | Base, Config, UI                                        | ❌        |
| `perspective-ui`                            | P:UI                  | Abstract Widgets, Abstract Screens, UI Events | Base, Config                                            | ❌        |
| `perspective-ui-background`                 | P:UI Background       | UI Background                                 | Base, Config, Shader Packs                              | ❌        |

✅ = Complete 🚧 = Work In Progress ❌ = Not Started Yet