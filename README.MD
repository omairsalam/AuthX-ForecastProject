# AuthX Forecast Automation Project

<p> The ForecastApp.com is a website which has a very neat tool used by many companies to keep a track of what projects they currently have, how many people are working on those projects, whether there is a shortfall or not, which projects are soon to be started, which are in the pipeline and so on. Therefore, as the name Forecast suggests, this website helps companies forecast their future work. </p> 

<p> This project stemmed from a need to automate many processes that were going on in AuthX at highly regular intervals. At the weekly resource meetings, a tedious process involving ascertaining data from a website called ForecastApp.com and then imnporting the data into Excel and then plotting the graphs took place to see what the data meant took place. The specific steps were: 
<ol> 
  <li> Obtain data from ForecastApp.com using the following method: 
  <ol>
    <li> Login to ForecastApp.com using one of the admin accounts </li> 
    <li> Navigate to the exports page </li> 
    <li> Click on the export button and save the text file containing the forecasted numbers of next week. </li> 
  </ol> 
  <li> Use that data to obtain graphs using the following method: 
    <li> Copy data from the text file into a .xlxs file </li>
    <li> Change parameters in the Excel workbook to output the graphs </li>
    <li> Manually remove data sets to toggle graphs as on or off</li>
  </ol>
</ol>

These steps were automated using a Java program called AutomaticHarvestClient which did the following: 
<ol> 
  <li> Used a webscrapping tool called Selenium to log into www.forecastapp.com, enter the login details and download the text file into a specific folder </li> 
  <li> Used java tools to sift through the data in order to obtain specific graphs for various roles, personell and projects </li>
  <li> Used plotting tools with APIs in Java to plot these graphs and even output their files as PNG files </li> 
</ol> 

</p>

<p> This is what the Automate Forecast App does i.e. it automates all the steps from acquiring the data to sifting through it to plotting it. </p> 
  
