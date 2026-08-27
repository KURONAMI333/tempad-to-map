# Tempad to Map

> See every Tempad teleport location on JourneyMap without copying coordinates by hand.

Tempad keeps your saved teleport locations in one place, while JourneyMap is
where you navigate. This addon sends each player's own Tempad locations from
the server and keeps matching JourneyMap waypoints up to date.

- Adds Tempad locations to JourneyMap as persistent waypoints.
- Syncs additions, changes, and removals automatically.
- Works in single-player, LAN, and dedicated-server worlds.
- Sends only each player's own locations.

## Supported loaders / versions

| Minecraft | NeoForge |
|---|:---:|
| 1.21.1 | ✅ |

Install this mod and Tempad on both the client and server. JourneyMap is
needed only on clients that should display the waypoints.

## Install

1. Install NeoForge 21.1.x for Minecraft 1.21.1.
2. Install [Tempad 3.x](https://www.curseforge.com/minecraft/mc-mods/tempad) on the client and server.
3. Install [JourneyMap](https://www.curseforge.com/minecraft/mc-mods/journeymap) on the client.
4. Put `tempadtomap-0.1.0+neoforge-1.21.1.jar` in the `mods` folder on each required side.

## Dependencies

- Required: [Tempad](https://www.curseforge.com/minecraft/mc-mods/tempad) 3.x on the client and server.
- Optional client display: [JourneyMap](https://www.curseforge.com/minecraft/mc-mods/journeymap).

## Usage

Create, edit, or delete a location in Tempad. The corresponding JourneyMap
waypoint is added, updated, or removed automatically within a few seconds.

## Scope & limitations

This is one-way synchronization from Tempad to JourneyMap. Editing a waypoint
in JourneyMap does not change Tempad. Only Tempad's saved player locations are
synced; other Tempad location providers are not included.

## License & credits

All Rights Reserved. Free to put in any modpack, on any platform, monetised or
not - no permission needed, no credit required. Source is published so you can
read exactly what it does.

Author: KURONAMI
