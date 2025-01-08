
## Changelog  
- Updated Config Version to `22`.  
### Bug Fixes  
- 12-hour time overlay no longer displays 12:00am as 012:00am.  
### Textured Entity  
- Textured entities can now be replaced by higher level resource packs.  
- Updated how the optional `enabled` variable in the textured entity dataloader format is used.  
  - The `enabled` tag will now instead be used as a check to see if the texture should be replaced.   
    - This allows for entities without a name (including invalid names), to have their texture replaced using the Textured Entity system.  
      - Use cases for this include replacing textures of entities that share a texture, such as spider eyes.  
### Resource Packs  
#### Perspective: Default  
- Added `default` `minecraft:cave_spider` textured entity.  
  - This entity will be rendered on cave spiders, that don't have a name.  