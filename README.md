# spacedoc

Spacecat's Awesome Documentation Renderer.

## What it is

A "simple" documentation renderer intended for use with digital electronics projects. Think FPGAs, state-machines,
microcontrollers, tooling, etcetera.

So far it is only intended to be used stand-alone for generating HTML out of extended markdown files.

## How do I build it

You will need:

- GraalVM JDK supporting Java 11+
    - Future revisions may not require GraalVM anymore; there is already some infrastructure in-place to make this
      almost happen.
      <br> By almost I mean it's not _quite_ happening yet, but it should be doable soon™. The only issue so far seems
      to be some missing dependencies while running on OpenJDK. Will-fix.
- Maven 3.6.3+
- Some luck

**TODO:** Actually document how it's supposed to be built. If you're feeling adventurous and impatient,
try `mvn clean install` as usual.

## How do I run it

**TODO:** Actually document how it's supposed to be ran. If you're feeling adventurous and impatient, try
running `in.spcct.spacedoc.exec.Main`.

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
- Write a better readme, perhaps even saying how to even run this
- Document usage, especially of the customized renderers
- Actually package releases, and indeed release something
- Add some way of using the customized renderers separately from the markdown stuff.
    - Not sure how necessary this is, this is quite easy to hack around so far.
- Documentation generation from VHDL/(System)Verilog/other projects
    - Parse all the comments
- All the other things!