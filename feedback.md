# MP1B: THE SOUND DATATYPE (Feedback)

## Grade: A

**Compiles**: Yes:Yes:Yes:Yes:Yes

**Timeout**: No:No:No:No:No

## Test Results
### cpen221.soundwaves.Task1Tests
| Test Status | Count |
| ----------- | ----- |
|tests|6|
|skipped|0|
|failures|1|
|errors|0|
#### Failed Tests
1. `testCreateSquareWaveZeroPhaseAmp2() (org.opentest4j.AssertionFailedError: expected: <1.0> but was: <2.0>)`

### cpen221.soundwaves.Task2Tests
| Test Status | Count |
| ----------- | ----- |
|tests|18|
|skipped|0|
|failures|3|
|errors|0|
#### Failed Tests
1. `echoTestNotEmpty3() (org.opentest4j.AssertionFailedError: array lengths differ, expected: <7> but was: <4>)`
1. `echoTestNotEmpty4() (org.opentest4j.AssertionFailedError: array lengths differ, expected: <6> but was: <4>)`
1. `echoTestNotEmpty5() (org.opentest4j.AssertionFailedError: array lengths differ, expected: <6> but was: <4>)`

### cpen221.soundwaves.Task3Tests
| Test Status | Count |
| ----------- | ----- |
|tests|17|
|skipped|0|
|failures|7|
|errors|0|
#### Failed Tests
1. `containsRandom() (org.opentest4j.AssertionFailedError: expected: <true> but was: <false>)`
1. `similarityBothZeroDiffLength() (java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 2)`
1. `similarityBothNonZeroDiffLength() (java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1)`
1. `similarityBothNonZero1() (org.opentest4j.AssertionFailedError: expected: <0.5348080338822119> but was: <0.431459732677024>)`
1. `similarityBothNonZero() (org.opentest4j.AssertionFailedError: expected: <0.9825309620671149> but was: <0.9457332430585895>)`
1. `similarityBothNonZeroPadded() (org.opentest4j.AssertionFailedError: expected: <0.86009083855719> but was: <0.8389355265485037>)`
1. `containsNotEmptyDiffLength() (org.opentest4j.AssertionFailedError: expected: <true> but was: <false>)`

### cpen221.soundwaves.Task4Tests
| Test Status | Count |
| ----------- | ----- |
|tests|7|
|skipped|0|
|failures|3|
|errors|0|
#### Failed Tests
1. `highPassFilter() (org.opentest4j.AssertionFailedError: expected: <true> but was: <false>)`
1. `lowPassFilter() (org.opentest4j.AssertionFailedError: expected: <true> but was: <false>)`
1. `bandPassFilter() (org.opentest4j.AssertionFailedError: expected: <true> but was: <false>)`

### cpen221.soundwaves.Task5Tests
| Test Status | Count |
| ----------- | ----- |
|tests|3|
|skipped|0|
|failures|0|
|errors|0|

## Code Coverage
### ConcreteSoundWave
| Metric | Coverage |
| ------ | -------- |
|LINE_MISSED|45|
|LINE_COVERED|223|
|BRANCH_MISSED|68|
|BRANCH_COVERED|138|
### SoundWaveSimilarity
| Metric | Coverage |
| ------ | -------- |
|LINE_MISSED|3|
|LINE_COVERED|61|
|BRANCH_MISSED|6|
|BRANCH_COVERED|38|
### TriangleWave
| Metric | Coverage |
| ------ | -------- |
|LINE_MISSED|0|
|LINE_COVERED|9|
|BRANCH_MISSED|0|
|BRANCH_COVERED|2|
### ComplexNumber
| Metric | Coverage |
| ------ | -------- |
|LINE_MISSED|0|
|LINE_COVERED|22|
|BRANCH_MISSED|0|
|BRANCH_COVERED|0|
### SinusoidalWave
| Metric | Coverage |
| ------ | -------- |
|LINE_MISSED|0|
|LINE_COVERED|9|
|BRANCH_MISSED|0|
|BRANCH_COVERED|2|
### SquareWave
| Metric | Coverage |
| ------ | -------- |
|LINE_MISSED|0|
|LINE_COVERED|9|
|BRANCH_MISSED|0|
|BRANCH_COVERED|4|

## Comments
### Overall
- Missing RIs and AFs for most classes
- Good specs overall
- General lack of comments throughout. Comments are extremely helpful, both to yourself when coming back later or for any other developer reading your code. They should be used to describe the 'why' and 'how' of your code.
- Good job pulling out repeated functionality into helper methods, but make sure they are private
- Generally good code style and variable names, but improvements can be mode. When unsure, err away from ambiguity

### Task 1
- Missing RI
- Incorrect AF. An AF describes how the class abstracts the representation of what it represents. The AF written here does not really state anything about the representation
- Good `checkRep()`
- Good spec on the constructor
- Good job cloning `leftChannel` and `rightChannel` before being returned from `getLeftChannel()` and `getRightChannel()`. Also, consider the `Arrays.asList()` method

### Task 2
- Generally good specs
- Spec for `append()` should explicity state that the method modifies the object, otherwise good spec (especially mentioning that the channel lengths should be the same)
- Spec for `add()` should mention the zero-padding being done if the lengths are not equal
- Spec for `scale()` should mention that normalization is performed if required after scaling
- `getRightChannelList()` and `getLeftChannelList()` should be private

### Task 3
- `contains()` and `similarity()` are pretty hard to read without comments, and variable names could be improved (what is `scale`? Why does it start at -1? Better variable names and comments would make this clear without having to read the code)
- Spec for `contains()` could be more explicit and mention that if the length of `other` is larger than `this`, it will return `false`

### Task 4
- On `ComplexNumber`: Incorrect AF, Good RI, Good specs
- Good specs. Spec for `filter()` should state if frequencies falling into the stated range are included or excluded
- Code is hard to understand quickly in `filter()` due to lack of comments. Consider adding small comments describing the purpose of certain chunks of code

### Task 5
- Good spec
- Code is a little messy, especially with no comments


**Detailed Code Analysis**

| Filename | Line | Issue | Explanation |
| -------- | ---- | ----- | ----------- |
|ConcreteSoundWave.java|9|	UnusedImports|	Unused import 'cpen221.soundwaves.soundutils.FilterType.*'|
|ConcreteSoundWave.java|11|	GodClass|	Possible God Class (WMC=118, ATFD=76, TCC=25.714%)|
|ConcreteSoundWave.java|11|	ModifiedCyclomaticComplexity|	The class 'ConcreteSoundWave' has a Modified Cyclomatic Complexity of 4 (Highest = 16).|
|ConcreteSoundWave.java|11|	StdCyclomaticComplexity|	The class 'ConcreteSoundWave' has a Standard Cyclomatic Complexity of 4 (Highest = 18).|
|ConcreteSoundWave.java|11|	CyclomaticComplexity|	The class 'ConcreteSoundWave' has a total cyclomatic complexity of 118 (highest 28).|
|ConcreteSoundWave.java|11|	TooManyMethods|	This class has too many methods, consider refactoring it.|
|ConcreteSoundWave.java|12|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|ConcreteSoundWave.java|13|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|ConcreteSoundWave.java|23|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|25|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|42|	UseVarargs|	Consider using varargs for methods or constructors which take an array the last parameter.|
|ConcreteSoundWave.java|46|	ArrayIsStoredDirectly|	The user-supplied array 'rightChannel' is stored directly.|
|ConcreteSoundWave.java|47|	ArrayIsStoredDirectly|	The user-supplied array 'leftChannel' is stored directly.|
|ConcreteSoundWave.java|99|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'templ' (lines '99'-'102').|
|ConcreteSoundWave.java|99|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'templ' (lines '99'-'105').|
|ConcreteSoundWave.java|100|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'tempr' (lines '100'-'108').|
|ConcreteSoundWave.java|100|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'tempr' (lines '100'-'111').|
|ConcreteSoundWave.java|102|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'templ' (lines '102'-'102').|
|ConcreteSoundWave.java|102|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'templ' (lines '102'-'105').|
|ConcreteSoundWave.java|105|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'templ' (lines '105'-'105').|
|ConcreteSoundWave.java|108|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'tempr' (lines '108'-'108').|
|ConcreteSoundWave.java|108|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'tempr' (lines '108'-'111').|
|ConcreteSoundWave.java|111|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'tempr' (lines '111'-'111').|
|ConcreteSoundWave.java|141|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'waveL' (lines '141'-'171').|
|ConcreteSoundWave.java|142|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'waveR' (lines '142'-'171').|
|ConcreteSoundWave.java|143|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'otherL' (lines '143'-'171').|
|ConcreteSoundWave.java|144|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'otherR' (lines '144'-'171').|
|ConcreteSoundWave.java|155|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'addedL' (lines '155'-'159').|
|ConcreteSoundWave.java|156|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'addedR' (lines '156'-'160').|
|ConcreteSoundWave.java|159|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'addedL' (lines '159'-'159').|
|ConcreteSoundWave.java|160|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'addedR' (lines '160'-'160').|
|ConcreteSoundWave.java|238|	UseVarargs|	Consider using varargs for methods or constructors which take an array the last parameter.|
|ConcreteSoundWave.java|245|	AvoidLiteralsInIfCondition|	Avoid using Literals in Conditional Statements|
|ConcreteSoundWave.java|259|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|336|	ModifiedCyclomaticComplexity|	The method 'contains' has a Modified Cyclomatic Complexity of 14.|
|ConcreteSoundWave.java|336|	StdCyclomaticComplexity|	The method 'contains' has a Standard Cyclomatic Complexity of 14.|
|ConcreteSoundWave.java|336|	CognitiveComplexity|	The method 'contains(SoundWave)' has a cognitive complexity of 31, current threshold is 15|
|ConcreteSoundWave.java|336|	CyclomaticComplexity|	The method 'contains(SoundWave)' has a cyclomatic complexity of 19.|
|ConcreteSoundWave.java|336|	NPathComplexity|	The method 'contains(SoundWave)' has an NPath complexity of 3680, current threshold is 200|
|ConcreteSoundWave.java|337|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|340|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'scale' (lines '340'-'346').|
|ConcreteSoundWave.java|340|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'scale' (lines '340'-'350').|
|ConcreteSoundWave.java|341|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|355|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|360|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|367|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|391|	CognitiveComplexity|	The method 'similarity(SoundWave)' has a cognitive complexity of 16, current threshold is 15|
|ConcreteSoundWave.java|391|	CyclomaticComplexity|	The method 'similarity(SoundWave)' has a cyclomatic complexity of 11.|
|ConcreteSoundWave.java|439|	AvoidLiteralsInIfCondition|	Avoid using Literals in Conditional Statements|
|ConcreteSoundWave.java|439|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|ConcreteSoundWave.java|439|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|ConcreteSoundWave.java|445|	AvoidLiteralsInIfCondition|	Avoid using Literals in Conditional Statements|
|ConcreteSoundWave.java|445|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|ConcreteSoundWave.java|445|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|ConcreteSoundWave.java|477|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'w1R' (lines '477'-'492').|
|ConcreteSoundWave.java|479|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'w2R' (lines '479'-'492').|
|ConcreteSoundWave.java|509|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'freqRes' (lines '509'-'533').|
|ConcreteSoundWave.java|511|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'leftDFT' (lines '511'-'533').|
|ConcreteSoundWave.java|512|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'combinedDFT' (lines '512'-'515').|
|ConcreteSoundWave.java|515|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'combinedDFT' (lines '515'-'515').|
|ConcreteSoundWave.java|518|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'maxFreqComp' (lines '518'-'533').|
|ConcreteSoundWave.java|519|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'maxFreq' (lines '519'-'526').|
|ConcreteSoundWave.java|525|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'maxFreqComp' (lines '525'-'533').|
|ConcreteSoundWave.java|526|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'maxFreq' (lines '526'-'526').|
|ConcreteSoundWave.java|550|	ModifiedCyclomaticComplexity|	The method 'filter' has a Modified Cyclomatic Complexity of 16.|
|ConcreteSoundWave.java|550|	StdCyclomaticComplexity|	The method 'filter' has a Standard Cyclomatic Complexity of 18.|
|ConcreteSoundWave.java|550|	NcssCount|	The method 'filter(FilterType, Double...)' has a NCSS line count of 60.|
|ConcreteSoundWave.java|550|	CognitiveComplexity|	The method 'filter(FilterType, Double...)' has a cognitive complexity of 34, current threshold is 15|
|ConcreteSoundWave.java|550|	CyclomaticComplexity|	The method 'filter(FilterType, Double...)' has a cyclomatic complexity of 28.|
|ConcreteSoundWave.java|550|	NPathComplexity|	The method 'filter(FilterType, Double...)' has an NPath complexity of 840, current threshold is 200|
|ConcreteSoundWave.java|554|	AvoidLiteralsInIfCondition|	Avoid using Literals in Conditional Statements|
|ConcreteSoundWave.java|555|	SystemPrintln|	System.out.println is used|
|ConcreteSoundWave.java|558|	SystemPrintln|	System.out.println is used|
|ConcreteSoundWave.java|565|	AvoidLiteralsInIfCondition|	Avoid using Literals in Conditional Statements|
|ConcreteSoundWave.java|566|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'filterFreqMax' (lines '566'-'624').|
|ConcreteSoundWave.java|567|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'filterFreqMin' (lines '567'-'624').|
|ConcreteSoundWave.java|569|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'filterFreqMax' (lines '569'-'624').|
|ConcreteSoundWave.java|570|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'filterFreqMin' (lines '570'-'624').|
|ConcreteSoundWave.java|574|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '574'-'583').|
|ConcreteSoundWave.java|574|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '574'-'586').|
|ConcreteSoundWave.java|574|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '574'-'594').|
|ConcreteSoundWave.java|574|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '574'-'598').|
|ConcreteSoundWave.java|574|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '574'-'606').|
|ConcreteSoundWave.java|574|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '574'-'609').|
|ConcreteSoundWave.java|576|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'freqRes' (lines '576'-'624').|
|ConcreteSoundWave.java|582|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '582'-'582').|
|ConcreteSoundWave.java|582|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '582'-'585').|
|ConcreteSoundWave.java|583|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '583'-'583').|
|ConcreteSoundWave.java|583|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '583'-'586').|
|ConcreteSoundWave.java|585|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '585'-'582').|
|ConcreteSoundWave.java|585|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '585'-'585').|
|ConcreteSoundWave.java|586|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '586'-'583').|
|ConcreteSoundWave.java|586|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '586'-'586').|
|ConcreteSoundWave.java|593|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '593'-'593').|
|ConcreteSoundWave.java|593|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '593'-'597').|
|ConcreteSoundWave.java|594|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '594'-'594').|
|ConcreteSoundWave.java|594|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '594'-'598').|
|ConcreteSoundWave.java|597|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '597'-'593').|
|ConcreteSoundWave.java|597|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '597'-'597').|
|ConcreteSoundWave.java|598|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '598'-'594').|
|ConcreteSoundWave.java|598|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '598'-'598').|
|ConcreteSoundWave.java|605|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '605'-'605').|
|ConcreteSoundWave.java|605|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '605'-'608').|
|ConcreteSoundWave.java|606|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '606'-'606').|
|ConcreteSoundWave.java|606|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '606'-'609').|
|ConcreteSoundWave.java|608|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '608'-'605').|
|ConcreteSoundWave.java|608|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'rightDFT' (lines '608'-'608').|
|ConcreteSoundWave.java|609|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '609'-'606').|
|ConcreteSoundWave.java|609|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'leftDFT' (lines '609'-'609').|
|ConcreteSoundWave.java|633|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|ConcreteSoundWave.java|634|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|ConcreteSoundWave.java|639|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|ConcreteSoundWave.java|639|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
## Checkstyle Results
### `ComplexNumber.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 58 | 28 | `'real' hides a field.` |
| 58 | 41 | `'imaginary' hides a field.` |
| 97 | 16 | `'real' hides a field.` |
| 98 | 16 | `'imaginary' hides a field.` |
| 110 | 46 | `'real' hides a field.` |
### `ConcreteSoundWave.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 454 | 66 | `',' is not followed by whitespace.` |
### `DFT.java`
| Line | Column | Message |
| ---- | ------ | ------- |
### `MP3Wave.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 3 | 8 | `Unused import - ca.ubc.ece.cpen221.mp1.utils.MP3Player.` |
### `Main.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 8 | 8 | `Unused import - java.io.File.` |
| 32 | 37 | `'100' is a magic number.` |
| 36 | 73 | `'0.2' is a magic number.` |
| 36 | 79 | `'0.1' is a magic number.` |
| 76 | 59 | `'6' is a magic number.` |
| 76 | 70 | `'10' is a magic number.` |
| 84 | None | `Line is longer than 120 characters (found 136).` |
| 88 | 30 | `'1000' is a magic number.` |
| 92 | 1 | `Comment has incorrect indentation level 0, expected is 12, indentation should be the same level as line 87.` |
### `SimilarPair.java`
| Line | Column | Message |
| ---- | ------ | ------- |
### `SinusoidalWave.java`
| Line | Column | Message |
| ---- | ------ | ------- |
### `SoundWave.java`
| Line | Column | Message |
| ---- | ------ | ------- |
### `SoundWaveSimilarity.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 23 | 5 | `Method getSimilarSounds length is 86 lines (max allowed is 80).` |
### `SquareWave.java`
| Line | Column | Message |
| ---- | ------ | ------- |
### `TriangleWave.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 29 | 9 | `'{' at column 9 should be on the previous line.` |
| 30 | 29 | `'*' is not followed by whitespace.` |
| 30 | 39 | `'/' is not followed by whitespace.` |
| 30 | 39 | `'/' is not preceded with whitespace.` |
### `Audio.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 1 | None | `File does not end with a newline.` |
| 208 | 68 | `'/' is not followed by whitespace.` |
| 208 | 68 | `'/' is not preceded with whitespace.` |
| 208 | 69 | `'3' is a magic number.` |
| 209 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 246 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 249 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 270 | 1 | `Block comment has incorrect indentation level 0, expected is 4, indentation should be the same level as line 284.` |
| 285 | 9 | `'if' construct must use '{}'s.` |
| 288 | 9 | `'if' construct must use '{}'s.` |
| 289 | 9 | `'if' construct must use '{}'s.` |
| 298 | 9 | `'if' construct must use '{}'s.` |
| 300 | 45 | `'8' is a magic number.` |
| 318 | 9 | `'if' construct must use '{}'s.` |
| 333 | 24 | `'line' hides a field.` |
| 334 | 13 | `Local variable 'BUFFER_SIZE' must be in camelCase, or consist of a single upper-case letter.` |
| 334 | 27 | `'4096' is a magic number.` |
| 346 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 349 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 371 | 13 | `Local variable 'READ_BUFFER_SIZE' must be in camelCase, or consist of a single upper-case letter.` |
| 371 | 32 | `'4096' is a magic number.` |
| 381 | None | `Line is longer than 120 characters (found 121).` |
| 393 | 42 | `'/' is not followed by whitespace.` |
| 393 | 42 | `'/' is not preceded with whitespace.` |
| 394 | None | `Line is longer than 120 characters (found 123).` |
| 394 | 56 | `'*' is not followed by whitespace.` |
| 394 | 56 | `'*' is not preceded with whitespace.` |
| 394 | 58 | `'+' is not followed by whitespace.` |
| 394 | 58 | `'+' is not preceded with whitespace.` |
| 394 | 64 | `'0xFF' is a magic number.` |
| 394 | 73 | `'8' is a magic number.` |
| 394 | 86 | `'*' is not followed by whitespace.` |
| 394 | 86 | `'*' is not preceded with whitespace.` |
| 394 | 92 | `'0xFF' is a magic number.` |
| 401 | None | `Line is longer than 120 characters (found 128).` |
| 402 | None | `Line is longer than 120 characters (found 128).` |
| 411 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 442 | 59 | `'16' is a magic number.` |
| 446 | 13 | `'if' construct must use '{}'s.` |
| 447 | 19 | `'*' is not followed by whitespace.` |
| 447 | 19 | `'*' is not preceded with whitespace.` |
| 448 | 19 | `'*' is not followed by whitespace.` |
| 448 | 19 | `'*' is not preceded with whitespace.` |
| 448 | 45 | `'8' is a magic number.` |
| 461 | 13 | `'}' at column 13 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 467 | 13 | `'}' at column 13 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 468 | None | `Line is longer than 120 characters (found 138).` |
| 473 | 13 | `'}' at column 13 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 477 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 521 | 28 | `'line' hides a field.` |
| 522 | 17 | `Local variable 'BUFFER_SIZE' must be in camelCase, or consist of a single upper-case letter.` |
| 522 | 31 | `'4096' is a magic number.` |
| 535 | 13 | `'}' at column 13 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 538 | 13 | `'}' at column 13 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 565 | 9 | `'if' construct must use '{}'s.` |
| 574 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 584 | 38 | `'1000' is a magic number.` |
| 585 | 21 | `'}' at column 21 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 602 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 618 | 9 | `'}' at column 9 should be on the same line as the next part of a multi-block statement (one that directly contains multiple blocks: if/else-if/else, do/while or try/catch/finally).` |
| 643 | 13 | `'for' construct must use '{}'s.` |
| 650 | 13 | `'if' construct must use '{}'s.` |
| 650 | 40 | `'*' is not followed by whitespace.` |
| 650 | 40 | `'*' is not preceded with whitespace.` |
| 663 | 13 | `'for' construct must use '{}'s.` |
| 678 | 23 | `'440.0' is a magic number.` |
| 680 | 40 | `'*' is not followed by whitespace.` |
| 680 | 40 | `'*' is not preceded with whitespace.` |
| 692 | 29 | `'10' is a magic number.` |
### `AudioFile.java`
| Line | Column | Message |
| ---- | ------ | ------- |
### `FilterType.java`
| Line | Column | Message |
| ---- | ------ | ------- |
### `SoundWaveChart.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 18 | None | `Line is longer than 120 characters (found 175).` |
| 57 | 13 | `'.' should be on the previous line.` |

