[![Build](https://img.shields.io/github/workflow/status/xdev-software/vaadin-date-range-picker/Master%20CI)](https://github.com/xdev-software/vaadin-date-range-picker/actions?query=workflow%3A%22Master+CI%22)
[![Latest version](https://img.shields.io/maven-central/v/com.xdev-software/vaadin-date-range-picker)](https://mvnrepository.com/artifact/com.xdev-software/vaadin-date-range-picker)
[![Build Develop](https://img.shields.io/github/workflow/status/xdev-software/vaadin-date-range-picker/Develop%20CI/develop?label=build%20develop)](https://github.com/xdev-software/vaadin-date-range-picker/actions?query=workflow%3A%22Develop+CI%22+branch%3Adevelop)

# vaadin-date-range-picker
A Vaadin Flow DateRangePicker implementation

![demo](demo.png)

## Installation
[Installation guide for the latest release](https://github.com/xdev-software/vaadin-date-range-picker/releases/latest#Installation)

## Run the Demo
* Checkout the repo
* Run ``mvn clean package``
* Navigate into ``vaadin-date-range-picker-demo`` 
* Run ``mvn jetty:run``
* Open http://localhost:8080


<details>
  <summary>Show example</summary>
  
  ![demo](demo.gif)
</details>


## Releasing
If the ``develop`` is ready for release, create a pull request to the ``master``-Branch and merge the changes

When the release is finished do the following:
* Merge the auto-generated PR (with the incremented version number) back into the ``develop``
* Add the release notes to the [GitHub release](https://github.com/xdev-software/vaadin-date-range-picker/releases/latest)
* Upload the generated release asset zip into the [Vaadin Directory](https://vaadin.com/directory) and update the component there

## Dependencies and Licenses
View the [license of the current project](LICENSE) or the [summary including all dependencies](https://xdev-software.github.io/vaadin-date-range-picker/dependencies/)
