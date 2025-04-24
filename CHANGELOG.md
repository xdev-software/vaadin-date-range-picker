## 4.2.1
* Fix naming so that Vaadin Directory sync works [#318](https://github.com/xdev-software/vaadin-addon-template/issues/318)
* Updated dependencies

## 4.2.0
* Improved styling
  * Overlay now has the same minimum width as the underlying DateRangePicker
    * The default is ``20em``, this can be changed using ``--date-range-picker-min-width``
  * Layouts inside the overlay now use the full available width
  * Removed excess top padding from input components
  * Refactored CSS class names (should cause less conflicts now)
* Updated to Vaadin 24.5

## 4.1.1
* Only use client-side locale for formatting when no ``formatLocale`` has been set #353

## 4.1.0
* Updated to Vaadin 24.4
* Minor code improvements

## 4.0.2
* ⚠️ GroupId changed from ``com.xdev-software`` to ``software.xdev``
* Updated dependencies

## 4.0.1
* Various dependency updates including Vaadin 24.1

## 4.0.0
⚠️<i>This release contains breaking changes</i>

* Adds support for Vaadin 24+, drops support for Vaadin 23<br/>
  <i>If you are still using Vaadin 23, use the ``3.x`` versions.</i>
  * Requires Java 17+
  * Fixed Broken overlay detection on Vaadin 24 #224 
* Added ``AllowRangeLimitExceeding``; default value is ``true``
* Updated dependencies

## 3.0.3
* Renamed ``defaultModel`` to ``initialModel``
* Updated dependencies

## 3.0.2
* Updated dependencies
  * Vaadin 23.3

## 3.0.1
* Updated dependencies
  * Vaadin 23.2

## 3.0.0
⚠️<i>This release contains breaking changes</i>

* Adds support for Vaadin 23+, drops support for Vaadin 14 #155<br/>
  <i>If you are still using Vaadin 14, use the ``2.x`` versions.</i>
  * Requires Java 11+
* Updated dependencies
