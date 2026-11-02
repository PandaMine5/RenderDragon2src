# RenderDragon2Source
A source code of Render Dragon by PandaMine5
Works on MCBE 1.21.70 - 1.21.100+ 

Requires: 
- Windows 11
- Python 3.12 + 
- Vs Code for editing and running   commands

## Setup

1. **Install Lazurite**  
   - Open a terminal
   - Run:  
     ```sh
     python -m pip install lazurite==0.12.0
     ```


## Compile Shaders

To compile materials using **Lazurite**, run the following command:


```bash

lazurite build "src" -p all_platform_compatibility

```