#include <math.h>
#include <stdio.h>
#include <iostream>
#include <vector>
#include <utility> 
#include <string>
using namespace std;

/*
* Function calculating the confidence interval for the excercise 
* @param v Vector of the input data 
* @return confidence_interval returns the confidence interval 
*/

double calculate_03_06(vector <int> v){
    double sum = 0;
for (int i = 0; i<v.size();i++){
     sum += v[i]; 
}
double mean = sum/v.size(); 
double squared_difference = 0 ;
    for (int j = 0; j<v.size();j++){
    squared_difference += pow((v[j]-mean),2); 
    }
double variance = squared_difference/v.size(); 
double standard_deviation = sqrt(variance); 
double confidence_interval = 1.96*(standard_deviation/sqrt(v.size())); 
return confidence_interval;
}
/* 
* Function calculating the operational reliability in percentage 
* @param failure_rate user input of failure rate 
* @param time user input of time 
* @return result returns the percentage of operational reliability 
*/
double  calcuate_01_03( double failure_rate , double time ) {
     
     double result = exp(-failure_rate * time); 
     result = result*100;
    return result;
}
/*
* Function to calculate failure rate 
* @param percentage description of the raliability in percentage
* @param time user input of time 
* @return failure_rate failure rate 
*/
double calculate_01_04(double percentage, int time){
    double decimal = percentage/100; 
    double failure_rate = -log(decimal) / time; 
    return failure_rate;
}
/**
 * Function calulating the reliability function and raliability after time
 * @param mtbf mean time between failure    
 * @param time time
 * @return pair<double, double> return a raliability function and reliability after time
 */
pair<double, double> calculate_01_05(double mtbf, double time){
    double failure_rate = 1.0/mtbf;
    double result = exp(failure_rate*time);
    return make_pair(failure_rate, result);
}
/**
 * Function calucting probability of of equipment operating without failure at given time
 * @param mtbf mean time between failures
 * @param time time 
 * @return double returns a probability of equipment operating without failure after given time 
 */
double calculate_01_06(double mtbf, double time) {
    double failure_rate = 1.0/mtbf;
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
        double failureRate;
        double time;
        cout<< "Please enter failure rate:  "<<endl;
        cin>>failureRate;
        cout<<"Please enter time in hours: "<<endl;
        cin>>time;
       cout<< calcuate_01_03(failureRate,time)<<endl;
    }
    else if(selection==2){
        double percentage;
        double time;
        cout<< "Please enter percentage:  "<<endl;
        cin>>percentage;
        cout<<"Please enter time in hours: "<<endl;
        cin>>time;
        cout<< calculate_01_04(percentage,time)<<endl;
    }
    else if (selection==3){
        pair<double,double> result;
        double mtbf;
        double time;
        cout<< "Please enter mean time between failures:  "<<endl;
        cin>>mtbf;
        cout<<"Please enter time in hours: "<<endl;
        cin>>time;
        result = calculate_01_05(mtbf,time);
        cout<<"Failure Rate : "<<result.first<<" Reliability : "<<result.second<<endl;
    }
    else if (selection==4){
        double mtbf;
        double time;
        cout<< "Please enter mean time between failures:  "<<endl;
        cin>>mtbf;
        cout<<"Please enter time in hours: "<<endl;
        cin>>time;
         cout<<calculate_01_06(mtbf,time)<<endl;
    }
    else if (selection==5){

vector<int> population = {17,25,32,10,6,5,8,9,12,17,46,64,83,70,10,15,2,8,29,11};
cout<<"+-"<<calculate_03_06(population)<<endl;;
    }
return 0;
}
