
# LittlePayTecketing
## Usage
Choose one test file from test folder

Rename the test file to ``input.csv`` and place `` LittlePayTicketing-1.0.jar`` and ``input.csv`` in the same directory

run ``$ java -jar LittlePayTicketing-1.0.jar``

Example
```
============== MENU ==============
1. add stops
2. calculate result
3. print result
4. exit
```

select ``calculate result`` before ``print result``
## How price table works
When add pricing data
```
Trips between Stop 1 and Stop 2 cost $3.25
Trips between Stop 2 and Stop 3 cost $5.50
Trips between Stop 1 and Stop 3 cost $7.30
```
Two table will be generated:
A:

|  | 1 | 2 |3|
|---|---|---|---|
|1 |-|3.25|7.30|
|2 |3.25|-|5.50|
|3 |7.35|5.50|-|
B:

|  | 1 |2|3|
|---|---|---|---|
|1 |-|3.25|7.30|
|2 |-|-|5.50|

Table B is used to regenerated Table A when user is adding new pricing data

E.g.
Trips between Stop 1 and Stop 4 cost $6.00
### 1. Allocate 6+2=8 spaces for the new table

|  | 1 | 2 |3|4|
|---| ---|---|---|---|
|1 |-|*|*|*|
|2 |*|-|*|-|
|3 |*|*|-|-|
|4| *|-|-|-|

### 2. Generate new A with old B and meanwhile generate new B

new A:

|  | 1 | 2 |3|4|
|---| ---|---|---|---|
|1 |-|3.25|7.30|*|
|2 |3.25|-|5.50|-|
|3 |7.35|5.50|-|-|
|4| *|-|-|-|

new B:

|  | 1 | 2 |3|4|
|--- | ---| ---| ---|---|
|1 |-|3.25|7.30|*|
|2 |-|-|5.50|-|
### 3. Add 1-4 and 4-1

new A:

|  | 1 | 2 |3|4|
|---|---|---|---|---|
|1 |-|3.25|7.30|6.00|
|2 |3.25|-|5.50|-|
|3 |7.35|5.50|-|-|
|4| 6.00|-|-|-|

new B:

|  | 1 | 2 |3|4|
|--- | ---| ---| ---|---|
|1 |-|3.25|7.30|6.00|
|2 |-|-|5.50|-|
## Output
``output.csv`` in the same directory

## Assumptions
1. Input file is well formed and is not missing data.
2. All User inputs are valid (input validation is not in place)
3. This scenario:
```
1, 22-01-2018 13:00:00,ON,Stop1,Company1,Bus37,5500005555555559
2, 22-01-2018 13:10:00,OFF,Stop1,Company1,Bus50,5500005555555559
```
4. This scenario:
```
1, 22-01-2018 13:00:00,OFF,Stop1,Company1,Bus37,5500005555555559
2, 22-01-2018 13:10:00,ON,Stop1,Company1,Bus37,5500005555555559
```
* in scenario 3 & 4 the "OFF" records will be ignored and the trip will be deemed "Incomplete"