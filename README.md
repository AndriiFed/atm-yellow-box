# atm-yellow-box

![Image of atm-yellow-box](https://github.com/AndriiFed/atm-yellow-box/blob/master/atm-yellow-box.jpg)

Cash machine
===================

#### Implement interface to ATM cash. The device receives commands from standard input and sends replies to standard output.

**Accepted commands:**

**Add notes**

**\+ \<currency\> \<value\> \<number\>**


\<currency\> is 3 uppercase letters, any combination is valid

\<value\> is the value of notes. Valid values are 10n and 5*10n, 0\<\=n\<\=3 
(although some currencies may have larger value notes and some odd values like 2,3,20,25, we do not allow them for simplification).

\<number\> is any positive integer

Semantics: puts notes into cash

Reply: OK if successful, ERROR if validation fails

*Example:*

\+ USD 100 30

OK

<br />
**Get cash**

**\- \<currency\> \<amount\>**

\<currency\> as described above, \<amount\> any positive integer

Semantics: get the sum from the cash if possible.

Reply: one line per each note value, formatted as

\<value\> \<number of notes\>, followed with a line OK

ERROR if the amount is unavailable (cash remains unchanged).

Example:

\- USD 2000

100 20

OK

<br />
**Print cash**

**\?**

Reply: one line for each currency/value pair

\<currency\> \<value\> \<number\>

followed by the line OK

Semantics: what is currently in the cash

Example: see in sample session


<br />
**Sample session (replies are in italics):**

**\+ USD 100 5**

*OK*

**\+ USD 10 30**

*OK*

**\+ EUR 100 20**

*OK*

**\+ EUR 1000 15**

*OK*

**\- UAH 100**

*ERROR*

**\- USD 260**

*100 2*

*10 6*

*OK*

**\- USD 125**

*ERROR*

**\- USD 600**

*ERROR*

**\- USD 500**

*100 3*

*10 20*

*OK*

**\?**

*EUR 100 20*

*EUR 1000 15*

*USD 10 4*

*OK*
