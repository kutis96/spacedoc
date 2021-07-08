# spacedoc

Spacecat's Awesome Documentation Renderer.

## Does it still build?

[![Maven CI Build](https://github.com/kutis96/spacedoc/actions/workflows/maven-ci.yml/badge.svg)](https://github.com/kutis96/spacedoc/actions/workflows/maven-ci.yml)

## What it is

A "simple" documentation renderer intended for use with digital electronics projects. Think FPGAs, state-machines,
microcontrollers, tooling, etc.

So far it is only intended to be used stand-alone for generating HTML out of extended markdown files, or to generate SVG
images using the built-in DSLs.

## How do I build it

### Prerequisites

You will need:

- Maven 3.6.3+
- Some luck

And either:

- GraalVM JDK supporting Java 11+
    - Supports direct calls of Node.js libraries using GraalVM's Polyglot API
        - This API can be leveraged with the configuration value `ffc.polyglot-js` set to `ENABLED` or `AUTO`
- or some other JDK supporting Java 11+, OpenJDK works.
    - Currently, `spacedoc` only supports using external JS runtime provided by Node.js' `npx`
        - External JS libraries can only be used with the configuration value `ffc.polyglot-js` set to `DISABLED`
            - **TODO:** Auto-detection of non-GraalVM runtimes is currently broken, do not use `AUTO` here (yet).
    - **TODO:** Investigate other JS runtimes for non-GraalVM targets
    - In this case, you will also need Node.js
        - Do also set the `ffc.npx-executable` value in config.
        - You will also want to _install_ the required NPX modules.
            - To do this, you will need to `npm --install wavedrom-cli` the following
            - Backstory, according to my understanding.
                - `npx` is used to run pre-packaged Node.JS CLI apps/binaries
                - For whatever reason, `npx` can download the packages it needs, but by default, it won't install them.
                  And it does this _every time_ it needs to run something. Leading to massive time penalties in case one
                  has to run this multiple times, and in case of SpaceDoc, for every code block to be rendered by, say,
                  WaveDrom.
            - Backstory, part II:
                - Why the hell would I want to run `wavedrom-cli` in the first place?
                    - Wavedrom supports running in the browser
                    - They even have a nice online API for generating SVGs for you
                    - But, I still wanted to have a nice, static HTML spat out, with neat SVGs rendered in without any
                      JS dependencies.
                        - That nice Wavedrom online SVG-generating API won't be online forever
                        - Packaging JS dependencies with the generated HTML site could work, however.
                - **TODO:** Add an option to "null" DSL support for certain languages, passing them through as "regular"
                  code blocks

### Build

Before building, you may want to read the [configuration](#How do I configure it) section of this readme.

Run the usual `mvn clean install`, right in the project's root directory.

Upon a successful build, the resulting `.jar` can be found
at `[project-root]/spacedoc-executable/target/spacedoc-[version].jar`.

This file can be renamed to `spacedoc.jar`, placed somewhere convenient, and used as described in
the [usage](#How do I run it) section of this readme.

## How do I configure it

**TODO:** Actually describe how to configure this.

Currently, the only way to configure this is by the use of a [.properties](https://docs.oracle.com/javase/7/docs/api/java/util/Properties.html) file located
at `[project-root]/spacedoc-config/src/main/resources/config`, which also gets built into the final `.jar`
at `config/resources`.

Currently, these values are supported:

**WARNING:** The names of config values _will_ likely change before the next release.

**TODO:** Autogenerate documentation of config values to keep things relevant

**TODO:** Add some way of aliasing config values

**TODO:** Standardize config value naming

**TODO:** Stop writing todos and use GitHub Issues instead

- `ffc.npx-executable`
    - Path to NodeJS' `npx` executable, or `npx.cmd` on Windows.
    - It currently does not default to whatever is found on `$PATH` yet.
    - Must be set when `ffc.polyglot-js` is `DISABLED`
- `ffc.polyglot-js`
    - Takes one of these values:
        - `ENABLED`
            - Enables use of GraalVM's Polyglot API
        - `DISABLED`
            - Disables use of GraalVM's Polyglot APIs, using _some fallback_ instead. This currently means that `npx`
              will be used instead.
        - `AUTO`
            - Automatically detects whether the Polyglot API can be used.
- `general.temp-file-prefix`
    - All `spacedoc` temporary file names will start with this prefix
    - May not be set, in which case `spacedoc-temp` will be used by default.
- `general.temp-directory-path`
    - Temporary file directory for temporary generated output files
        - Typically used by external library calls, such as `wavedrom-cli`.
    - May not be set, in which case the default system temp path is used.
- `polyglot.js.require.directory`
    - Path to a directory to store NodeJS module dependencies
    - May not be set, in which case `js/require` on the current classpath (think the same directory the `spacedoc.jar`
      is in) is used.
        - **TODO:** Verify

## How do I run it

Usage:

- `java -jar spacedoc.jar help` - prints info about some basic usage
- `java -jar spacedoc.jar [module name] [arguments...]`
- `java -jar spacedoc.jar markdown [arguments...]`
    - `-h` or `-help` - prints more help about markdown rendering, optional
    - `-i [path]` or `-input [path]` - path to the input (markdown) file
    - `-o [path]` or `-output [path]` - path to the output (HTML) file, optional
    - `-head [path]` - path to a file to copy at the beginning of the output file, optional
    - `-tail [path]` - path to a file to copy at the end of the output file, optional
- `java -jar spacedoc.jar langRender [arguments...]`
    - `-h` or `-help` - prints more help about DSL rendering, optional
    - `-list` - prints the name of all languages that can be rendered using the built-in DSLs, optional
    - `-lang [language]` - specifies the language to use for rendering
    - `-i [path]` or `-input [path]` - path to the input (DSL) file
    - `-o [path]` or `-output [path]` - path to the output (SVG) file, optional

There are currently no releases, so you'll have to [build your own](#How do I build it) `spacedoc.jar` to use it.

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

## Planned Features / Wishlist

This list has been ordered roughly in the expected order of implementation.

Some to all of these features may never get implemented, so don't get your hopes up. However, this project is nice to
procrastinate with, so who knows!

- Write a better readme, perhaps even saying how to even run this
- Document usage, especially of the customized renderers
    - Add some demos
- Do configuration a bit better
    - Add some way of loading the config file from somewhere else
    - Add some way of adding/modifying config options on the command line
    - Add some way of generating a config file with default values
- Themes/skinning/styling
    - Add support for basic styling/skinning
        - BitField renderer
    - Pass styles to the external renderers
        - Generate Graphviz color schemes
            - **TODO:** Research
            - Viz.js support?
        - Generate Wavedrom skins
- Templates!
    - Best technical blog tool ever (jk?)
    - Table-of-contents generation
- Actually package releases, and indeed release something
    - GraalVM's `native-image`
- Add some actual unit tests
- A formalized plugin system
    - We're mostly there, I guess. Just add some way of loading external plugins and registering stuff before the main
      stuff runs.
- Documentation generation from VHDL/(System)Verilog/other projects
    - Parse all the doc-strings!
- All the other things!
