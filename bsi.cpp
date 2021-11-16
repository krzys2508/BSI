#include <math.h>
#include <stdio.h>
#include <iostream>
#include <vector>
#include <utility> 
#include <string>
using namespace std;


double calculate_03_06(vector <int> v){
    double sum = 0;
for (int i = 0; i<v.size();i++){
     sum += v[i]; //Creating a sum of results needed for mean
}
double mean = sum/v.size(); // Calculating the mean by dividing the total sum by total number of results
double squared_difference = 0 ;
    for (int j = 0; j<v.size();j++){
    squared_difference += pow((v[j]-mean),2); // Calculating the squared difference needed for standard deviation
    }
double variance = squared_difference/v.size(); // Calculating variance also needed for standard deviation
double standard_deviation = sqrt(variance); //Calculating standard deviaiton
double confidence_interval = 1.96*(standard_deviation/sqrt(v.size())); // Getting the confidence Interval 1.96 is the number always multiplied to get 95% confidence interval
return confidence_interval;
}

double  calcuate_01_03( double failure_rate , double time ) {
     // user inputs failure rate and time, in order to get operational reliability we follow the formual e^(failure rate * time)
     double result = exp(-failure_rate * time); //
     result = result*100; // Multiply the result to get percentage 
    return result;
}

double calculate_01_04(double percentage, int time){
    double decimal = percentage/100; // converting percentage to decimal 
    double failure_rate = -log(decimal) / time; // as we do not know the failure rate we reverse the forumla
    return failure_rate;
}
pair<double, double> calculate_01_05(double mtbf, double time){
    //failure_rate = 1/MTBF 
    double failure_rate = 1.0/mtbf;
    //In ordere to find the reliability of the item at 30 hours, insert time into the formula 
    double result = exp(failure_rate*time);
    return make_pair(failure_rate, result);
}
double calculate_01_06(double mtbf, double time) {
    //failure rate = 1/mtbf
    double failure_rate = 1.0/mtbf;
    //Inputting the values into the formula 
    double result = exp( -time * failure_rate);
    return result;
}
int main (){

     cout<< "Select task :"<<endl;
    cout<<"1 = 01.03"<<endl;
    cout<<"2 = 01.04"<<endl;
    cout<<"3 = 01.05"<<endl;
    cout<<"4 = 01.06"<<endl;
    cout<<"5 = 03.06"<<endl;
    int selection; 
    cin>> selection;
    if (selection == 1 ){
       cout<< calcuate_01_03(0.000001,2500)<<endl;
    }
    else if(selection==2){
        cout<< calculate_01_04(98,5000)<<endl;
    }
    else if (selection==3){
        pair<double,double> result;
        result = calculate_01_05(25,30);
        cout<<"Failure Rate : "<<result.first<<" Reliability : "<<result.second<<endl;
    }
    else if (selection==4){
         cout<<calculate_01_06(1000,500)<<endl;
    }
    else if (selection==5){

vector<int> population = {17,25,32,10,6,5,8,9,12,17,46,64,83,70,10,15,2,8,29,11};
cout<<calculate_03_06(population)<<endl;;
    }
return 0;
}
