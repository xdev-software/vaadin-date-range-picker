[![Published on Vaadin Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/daterangepicker-for-vaadin)
[![Latest version](https://img.shields.io/maven-central/v/com.xdev-software/vaadin-date-range-picker)](https://mvnrepository.com/artifact/com.xdev-software/vaadin-date-range-picker)
[![Build](https://img.shields.io/github/workflow/status/xdev-software/vaadin-date-range-picker/Check%20Build/develop)](https://github.com/xdev-software/vaadin-date-range-picker/actions/workflows/checkBuild.yml?query=branch%3Adevelop)
![Vaadin 23+](https://img.shields.io/badge/Vaadin%20Platform/Flow-23+-00b4f0.svg)

# vaadin-date-range-picker
A Vaadin Flow DateRangePicker implementation

![demo](demo.png)

## Installation
[Installation guide for the latest release](https://github.com/xdev-software/vaadin-date-range-picker/releases/latest#Installation)

#### Compatibility with Vaadin

| Vaadin version | DateRangePicker version |
| --- | --- |
| Vaadin 23+ (latest) | ``3+`` |
| Vaadin 14 (LTS - former release model) | ``2.x`` |


## Run the Demo
* Checkout the repo
* Run ``mvn clean install``
* Navigate into ``vaadin-date-range-picker-demo`` 
* Run ``mvn jetty:run``
* Open http://localhost:8080


<details>
  <summary>Show example</summary>
  
  ![demo](demo.gif)
</details>


## Releasing [![Build](https://img.shields.io/github/workflow/status/xdev-software/vaadin-date-range-picker/Release?label=Release)](https://github.com/xdev-software/vaadin-date-range-picker/actions/workflows/release.yml)
If the ``develop`` is ready for release, create a pull request to the ``master``-Branch and merge the changes

When the release is finished do the following:
* Merge the auto-generated PR (with the incremented version number) back into the ``develop``
* Add the release notes to the [GitHub release](https://github.com/xdev-software/vaadin-date-range-picker/releases/latest)
* Upload the generated release asset zip into the [Vaadin Directory](https://vaadin.com/directory) and update the component there

## Dependencies and Licenses
View the [license of the current project](LICENSE) or the [summary including all dependencies](https://xdev-software.github.io/vaadin-date-range-picker/dependencies/)
