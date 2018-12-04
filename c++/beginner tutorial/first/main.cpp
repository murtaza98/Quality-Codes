#include <iostream>

using namespace std;

int main()
{
    cout << "Hello World!" << endl;		//endl will end the line, next o/p will be on new line
	cout << "Murtaza" << endl;

	//variables
	string charName = "Murtaza";
	int charAge = 20;
	cout << "Hello " << charName << endl;
	cout << "Your age is " << charAge << " years" << endl;

	//datatypes
	char grade = 'A';
	string phrase = "Academy";
	int age = 20;
	float decimalNo = 2.55;
	double doubleNo = 2.55555;
	bool isMale = true;

	//indexing a string
	string academy = "Giraffe Academy";
	cout << "first char is " << academy[0] <<endl;
	cout << "Result of find " academy.find("Academy",0) << endl;
	cout << "Substring " academy.substr(8,3) << endl; //substr(start_index,length)

    //input from user
    cout << "Enter age ";
    cin >> age;
    //string input
    getline(cin,name);

    return 0;
}
