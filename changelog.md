# Perspective 1.3 (Beta 1)
It's been such a long time since I've been able to update Perspective.
This is due to Minecraft 1.21.5 through to the current latest version having had so many changes behind the scenes, and I've been busy with other projects (You should check out [Somnium Reale](https://modrinth.com/mod/dtaf2026)!)  

With Luminance being updated, this has given me the push to update Perspective, however to make it easier to update in the future, I'm going to be splitting Perspective into several modules.
These modules will be available as both their own mods, and bundled together as Perspective (under the version 2.0).

1.3 will continue to be updated in the meantime, but won't contain any new features.
See [The future of Perspective](https://github.com/mclegoman/perspective/issues/22) for more details.

> Important note for users upgrading from older versions  
> This beta contains major changes, and won't be compatible with older addons, configs, or keybindings.
> You will need to resetup your config and keybindings, however this shouldn't need to be done again for Perspective 2.0.

## Changes
- Updated to 1.21.11
- Updated Luminance to 1.1.0-alpha.1
- Removed Shader Packs*
  - *They have been moved to Luminance under the name Shader Stacks.
    - All custom shaders will need to be updated anyway, and this allows for greater mod compatibility.
- Hold Perspective will no longer switch to first person when you are already in third person.
  - Whilst this was a useful feature, it was difficult to make sure you'd return to your intended perspective if you changed perspective manually whilst holding.
    - Swap Perspective will now switch what perspective you will go to after you let go.
- Zoom has been given an overhaul, though you probably won't see any differences unless you have an addon.