# About

This project was created to automatize some tasks at my archviz job.

It is not a general purpose tool, it was created for my specifc needs , however some of its functionalities may be usefull to someone.

---
# Funcionalities

It has the following capabilities:

- **Create Render Script:** Creates a script to batch render all projects (3ds max only).
- **Create Folder Structure:** Moves all .max files to structured project folders. The subfolders created are defined in the app.properties file.
- **Clear Logs:** Batch rendering of .max files generates logs in UTF-16 format with characters that should not be present. This method cleans the logs and saves them in UTF-8 format.
- **List Projects:** Lists all projects and enumerates them starting from 0.

---
## Observation

Note that **all methods (except clear logs)** must receive a root path containing all projects. Example:

- Client 01
	- Living room
	- Bedroom
	- Bathroom

All functions should get the Client 01 path.
