#include<stdio.h>
#include<time.h>
int sum(int a, int b){
	return a+b;
}

int sumall(int* values, int len){
    int result;
    for (int i = 0; i<len; i++){
        result = sum(result, values[i]);
    }
    return result;
}

int sub(int a, int b){

    if (1){

    }
    for (int i = 0; i<3; i++){
        printf("ad");
    }
    return sum(b, -a);
}



int main(){
    int a = 1;
    printf("%i", a);
    int b = 2;
    float bc = .5;
    int abcd;

    printf("%i", sum(11, 1111));
	printf("Hello world!");
	printf("%i", sum(a, b));


}


