## Installation

Starting server

    gradle bootRun


Open url `localhost:8080`

##Testing

To run tests

    gradle test
    
## Checkstyle    
    
Use the following command to run checkstyle
    
    gradle clean check

Command will show violations in console and also
generate reports in XML and HTML formats.     

Reports are located inside _reports/checkstyle_ folder.

To open report 
> $ firefox build/reports/checkstyle/main.html

