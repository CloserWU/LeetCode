#ifndef DATE_H
#define DATE_H
class Date {
public:
    Date() : year(0), month(0), day(0) {};
    Date(int year, int month, int day) : year(year), month(month), day(day) {};


    int getYear(){return this->year;};
    int getMonth(){return this->month;};
    int getDay(){return this->day;}
private:
    int year;
    int month;
    int day;
};

#endif // DATE_H
