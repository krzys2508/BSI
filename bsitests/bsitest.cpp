
#include <iostream>
#include <gtest/gtest.h>
#include <iostream>
#include <math.h>
#include <stdio.h>
#include <iostream>
#include <vector>
#include <utility> 
#include <string>
using namespace std;


double calculate_03_06(vector <int> v) {
    double sum = 0;
    for (int i = 0; i < v.size(); i++) {
        sum += v[i];
    }
    double mean = sum / v.size();
    double squared_difference = 0;
    for (int j = 0; j < v.size(); j++) {
        squared_difference += pow((v[j] - mean), 2);
    }
    double variance = squared_difference / v.size();
    double standard_deviation = sqrt(variance);
    double confidence_interval = 1.96 * (standard_deviation / sqrt(v.size()));
    return confidence_interval;
}
/*
* Function calculating the operational reliability in percentage
* @param failure_rate user input of failure rate
* @param time user input of time
* @return result returns the percentage of operational reliability
*/
double  calcuate_01_03(double failure_rate, double time) {

    double result = exp(-failure_rate * time);
    result = result * 100;
    return result;
}
/*
* Function to calculate failure rate
* @param percentage description of the raliability in percentage
* @param time user input of time
* @return failure_rate failure rate
*/
double calculate_01_04(double percentage, int time) {
    double decimal = percentage / 100;
    double failure_rate = -log(decimal) / time;
    return failure_rate;
}
/**
 * Function calulating the reliability function and raliability after time
 * @param mtbf mean time between failure
 * @param time time
 * @return pair<double, double> return a raliability function and reliability after time
 */
pair<double, double> calculate_01_05(double mtbf, double time) {
    double failure_rate = 1.0 / mtbf;
    double result = exp(failure_rate * time);
    return make_pair(failure_rate, result);
}
/**
 * Function calucting probability of of equipment operating without failure at given time
 * @param mtbf mean time between failures
 * @param time time
 * @return double returns a probability of equipment operating without failure after given time
 */
double calculate_01_06(double mtbf, double time) {
    double failure_rate = 1.0 / mtbf;
    double result = exp(-time * failure_rate);
    return result;
}

TEST(multiplicationTest, positiveTimeInput) {
    EXPECT_EQ((2 * 2), 2);
}

TEST(multiplicationTest, negativeTimeInput) {
    EXPECT_EQ((-2 * -2), 2);
}
TEST(multiplicationTest, zero) {
    EXPECT_EQ((0 * 0), 0);
}

TEST(calc0103, expectCorrectOutputWithCorrectInput) {
    double result = calcuate_01_03(2, 2);
    EXPECT_DOUBLE_EQ(result, 1.83156);
}
TEST(calc0103, expectIncorrectOutputWithIncorrectInput) {
    double result = calcuate_01_03(3, 3);
    EXPECT_DOUBLE_EQ(result, 1.83156);
}
TEST(calc0104, expectCorrectOutputWithCorrectInput) {
    double result = calculate_01_04(2, 2);
    EXPECT_DOUBLE_EQ(result, 1.95601);
}
TEST(calc0104, expectIncorrectOutputWithIncorrectInput) {
    double result = calculate_01_04(3, 2);
    EXPECT_DOUBLE_EQ(result, 1.95601);
}
TEST(calc0105, expectCorrectOutputWithCorrectInput) {
    pair<double,double> result = calculate_01_05(2, 2);
    EXPECT_DOUBLE_EQ(result.first, 0.5);
    EXPECT_DOUBLE_EQ(result.second, 2.71828);
}
TEST(calc0105, expectIncorrectOutputWithIncorrectInput) {
    pair<double, double> result = calculate_01_05(3,2);
    EXPECT_DOUBLE_EQ(result.first, 0.5);
    EXPECT_DOUBLE_EQ(result.second, 2.71828);
}
TEST(calc0106, expectCorrectOutputWithCorrectInput) {
    double result = calculate_01_06(2, 2);
    EXPECT_DOUBLE_EQ(result, 0.367879);
}
TEST(calc0106, expectIncorrectOutputWithIncorrectInput) {
    double result = calculate_01_06(3, 2);
    EXPECT_DOUBLE_EQ(result, 0.367879);
}
TEST(calc0306, expectCorrectOutputWithCorrectInput) {
    vector <int> v = { 17,25,32,10,6,5,8,9,12,17,46,64,83,70,10,15,2,8,29,11 };
    double result = calculate_03_06(v);
    EXPECT_DOUBLE_EQ(result, 10.0711);
}
TEST(calc0306, expectIncorrectOutputWithIncorrectInput) {
    vector <int> v = { 17,25,32,10,6,5,8,9,12,17,46,64,83,70,10,15,2,8,29,16 };
    double result =calculate_03_06(v);
    EXPECT_DOUBLE_EQ(result, 10.0711);
}
TEST(calc0103, expectIncorrectOutputWithIncorrectInput) {
    double result = calcuate_01_03(3, 3);
    EXPECT_EQ(result, 1.83156);
}

int main(int argc, char*argv[])
{
    
	testing::InitGoogleTest(&argc, argv);
	return RUN_ALL_TESTS();
}
