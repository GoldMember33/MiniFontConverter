Changelog - Version 1.0.2:

  * Added:
    - Added suggest and copy converted text components utility buttons to the messages while using
      conversion commands.
    - Added more configurable message options.
    - Added ExtraFiles folder with additional configurations files (default and SmallCaps font), 
      only for drag and drop contents to the messages.yml easily.
  
  * Changed:
    - Changed some class names.
    - Migrated default getters to Lombok annotations for cleaner code.

Changelog - Version 1.0.3:

  * Added:
    - Added some more utility classes to handle common tasks.
    - Added translations for suggested and copy converted message components.
    - Added a wildcard permission to allow access to all plugin commands.
    - Added permission checks to command tab completions, avoiding to non-allowed
      players to see commands they can't use.

  * Changed:
    - Organization of the code to improve readability and maintainability.
    - Updated the way messages are handled to allow for easier customization.
    - Move the message values to a separate file (messages.yml) for better organization.
    - Removed unnecessary message settings.
 
  * Fixed:
    - /minifontreload command now correctly reloads messages settings.
