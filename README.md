# spacedoc

Spacecat's Awesome Documentation Renderer.

## What it is

A "simple" documentation renderer intended for use with digital electronics projects. Think FPGAs, state-machines,
microcontrollers, tooling, etcetera.

So far it is only intended to be used stand-alone for generating HTML out of extended markdown files.

## Features

- [commonmark-java](https://github.com/commonmark/commonmark-java) -based rendering
- Multiple language renderers for fenced code blocks
    - Javascript-based renderers - Thanks [GraalVM](https://www.graalvm.org/) for making this interop possible.
    - [wavedrom](https://github.com/wavedrom/wavedrom) integration - use language "wavedrom".
    - [viz.js](https://github.com/mdaines/viz.js/) integration - use language "graphviz".
    - Custom Wavedrom-like bit-field renderers
        - "bitfield" for relatively generic bit field drawings.
        - "memory-map" renderer for simplified memory map tables.
        - "instruction-set" renderer for simplified instruction format listings.
    - Fairly simple infrastructure for further extension
- Not much else so far.

## Planned Features

This list has been ordered roughly in the expected order of implementation.

Some to all of these features may never get implemented, so don't get your hopes up. However, this project is nice to
procrastinate with, so who knows!

- Templates
    - Best technical blog tool ever (jk)
- Table-of-contents generation
- Documentation generation from VHDL/(System)Verilog projects
    - Parse all the comments
- All the other things!