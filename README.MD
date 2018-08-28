## Installation

Starting server

    gradle bootRun


Open url `localhost:8080`

##Testing

To run tests

    gradle test
    
    
Use the following command to generate coverage report
    
    gradle clean test jacocoTestReport
    
Reports are located inside _build/reports/jacoco_ folder.

To open report 

    firefox build/reports/jacoco/index.html
    
## Checkstyle    
    
Use the following command to run checkstyle
    
    gradle clean check

Command will show violations in console and also
generate reports in XML and HTML formats.     

Reports are located inside _build/reports/checkstyle_ folder.

To open report 

    firefox build/reports/checkstyle/main.html

