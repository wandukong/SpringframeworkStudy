
# Input / Output

## 👮‍♂️InputStream
> 바이트 기반 입력 스트림의 최상위 클래스로 추상 클래스이다.
- BufferedInputStream, DataInputStream, FileInputStream이 InputStream 클래스를 상속하고 있다.

**메소드**
- read() : 1바이트를 읽고, 읽은 바이트를 리턴한다.
- read(byte[] b) : 데이터를 읽고, b에 저장한다.  실제로 읽은 바이트 수를 리턴한다.
- read(byte[] b, int off, int len) : b의 배열에서 어디서부터 얼마나 읽을것인지 지정한다, 읽은 길이 리턴
- close() : 사용한 시스템 자원을 반납하고 출력스트림을 닫는다.


## 👮‍♀️Outpstream
> 바이트 기반 출력 스트림의 최상위 클래스로 추상 클래스이다.
- FileOutputStream, DataOutputStream, PrintStream이 BufferedOutputStream 클래스를 상속하고 있다.

**메소드**
- write() : 1바이트를 출력한다.
- write(byte[] b) : b 배열의 크기만큼 출력한다.  
- write(byte[] b, int off, int len) : b의 배열에서 어디서부터 얼마나 출력할것인지 지정한다, 
- close() : 사용한 시스템 자원을 반납하고 출력스트림을 닫는다.
- flush() : 버퍼에 잔류하는 모든 바이트를 출력한다.
<hr />

### InputStream + OutputStream 
```java
InputStream is = new FileInputStream("C:/Temp/photo14.jpg");
OutputStream os = new FileOutputStream("C:/Temp/" + new Date().getTime() + "photo14.jpg");

byte[] data = new byte[1000];
int readByteNum = -1;
while(true) {
	readByteNum = is.read(data);
	if(readByteNum == -1 ) 
		break;
	os.write(data, 0, readByteNum); 
	os.flush();
}
os.close();
```

## 🕵️‍♂️Reader 
> 문자 기반 입력 스트림의 최상위 클래스로 추상 클래스이다.
- FileReader, BufferedReader, InputStreamReader는 모두 Reader 클래스를 상속한다.
- read() : 한개의 문자를 읽고, 읽은 문자를 리턴한다.
- read(char[] c) : 데이터를 읽고, c에 저장한다.  실제로 읽은 문자 수를 리턴한다.
- read(char[] c, int off, int len) : c의 배열에서 어디서부터 얼마나 읽을것인지 지정한다, 읽은 문자 길이 리턴
- close() : 사용한 시스템 자원을 반납하고 출력스트림을 닫는다.

## 🕵️‍♀️Writer
> 문자 기반 출력 스트림의 최상위 클래스로 추상 클래스이다.
- FileWriter, BufferedWriter, PrintWriter, OutputStreamReader는 모두 Writer 클래스를 상속한다.
- write(char[] c) : c 배열의 크기만큼 출력한다.  
- write(char[] c, int off, int len) : c의 배열에서 어디서부터 얼마나 출력할것인지 지정한다, 
- close() : 사용한 시스템 자원을 반납하고 출력스트림을 닫는다.
- flush() : 버퍼에 잔류하는 모든 바이트를 출력한다.
<hr />
 
### Reader + Writer
```java
Reader reader = new FileReader("C:/Temp/ReadExample1.java");
Writer writer = new FileWriter("C:/Temp/" + new Date().getTime()+ "ReadExample1.java");

char[] data = new char[1000];
int readCharNum = -1;
while(true) {
	readCharNum = reader.read(data);
	if(readCharNum==-1) break;
	writer.write(data, 0, readCharNum);
	writer.flush();
}
writer.close();
```

## 🎅File
> 파일 크기, 파일 속성, 파일 이름 등의 정보를 얻어내는 기능과 파일 생성 및 삭제 기능을 제공한다.
> 디렉토리를 생성하고, 디렉토리에 존재하는 파일 리스트를 얻어내는 기능도 있다.
> 데이터를 읽고 쓰는 기능은 지원하지 않는다.
```java
File file = new File("C:/Temp/ReadExample1.java");
```
**.exists()** : 존재 하는지 여부
#### .exist() 메소드가 false를 반환하면
- .mkdirs() : 경로상 모든 폴더 만들기
- .mkdir() : 새로운 폴더 만들기
- .createNewFile() : 새로운 파일 생성

### .exist() 메소드가 true를 반환하면
- .length() : 파일 크기
- .delete() : 파일 삭제


## 💂‍♂️FileInputStream
> 파일로부터 바이트 단위로 읽어들일 때 사용하는 바이트 기반 입력스트림이다.
> 바이트 단위로 읽기 때문에 그림, 오디오, 비디오, 텍스트 파일 등 모든 종류의 파일을 읽을 수 있다.

### FileInputStream 생성
```java
FileInputStream fis = new FileInputStream("C:/Temp/forest.jpg");

File file = new File("C:/Temp/forest.jpg");
FileInputstream fis = new FileInputStream(file);
```

## 💂‍♀️FileOutputStream
> 바이트 단위로 데이터를 파일에 저장할 때 사용하는 바이트 기반 출력 스트림이다.
> 바이트 단위로 저장하기 때문에 그림, 오디오, 비디오, 텍스트 파일 등 모든 종류의 데이터를 파일로 저장할 수 있다.

### FileOutputStream 생성
```java
FileOutputStream fos = new FileOutputStream("C:/Temp/forest2.jpg");

File file = new File("C:/Temp/forest2.jpg");
FileOutputStream fos = new FileOutputStream(file);
```
**기존의 파일에 데이터를 추가할 경우**
- 두 번째 매개변수에 true 지정
```java
FileOutputStream fos = new FileOutputStream("C:/Temp/forest2.jpg", true);
```

### FileInputStream + FileOutputStream
```java
InputStream is = new FileInputStream("C:/Temp/forest.jpg");
OutputStream os = new FileOutputStream("C:/Temp/forest2.jpg");

byte[] data = new byte[1000];
int readByteNum = -1;
while(true) {
	readByteNum = is.read(data);
	if(readByteNum == -1 ) 
		break;
	os.write(data, 0, readByteNum); 
	os.flush();
}
os.close();
```

# 보조 스트림(필터 스트림)
> 다른 스트림과 연결되어 여러 가지 편리한 기능을 제공해주는 스트림, 필터 스트림이라고도 불림
- 자체적으로 입출력 수행 불가
- 보조 스트림은 또 다른 보조 스트림에도 연결할 수 있다.
```
보조스트림 변수 = new 보조스트림(메인스트림);
```

## 👨‍🏫문자 변환 보조 스트림
> 바이트 기반 스트림보다 편하고, 문자셋의 종류를 지정할 수 있기 때문에 다양한 문자를 입출력할 수 있게 한다.

### InputStreamReader
> 바이트 입력 스트림에 연결되어, 문자 입력 스트림인 Reader로 변환시켜는 보조 스트림

```java
Reader reader = new InputStreamReader(바이트입력스트림);
```
```java
InputStream is = System.in; // 바이트 단위
Reader reader = new InputStreamReader(is); // System.in을 Reader로 바꿔주는 보조 스트림을 붙인다
```
<hr />

### OutputStreamReader
> 바이트 출력 스트림에 연결되어, 문자 출력 스트림인 Writer로 변환시켜는 보조 스트림
>
```java
Writer writer = new OutputStreamReader(바이트입력스트림);
```
```java
FileOutputStream fos = new FileOutputStream("C:/Temp/forest2.jpg"); // 바이트 단위
Writer writer = new OutputStreamReader(fos);	
```

## 👩‍🏫성능 향상 보조 스트림
> 프로그램이 입출력 소스와 직접 작업하지 않고, 중간에 버퍼와 작업함으로써 실행 성능을 향상시킬 수 있다.
- 버퍼는 데이터가 쌓이기를 기다렸다가 꽉 차게 되면 데이터를 한꺼번에 하드 디스크로 보냄으로써 출력 횟수를줄여준다.

### BufferedInputStream과 BufferdReader
- 입력 데이터로부터 자신의 내부 버퍼 크기만큼 데이터를 미리 읽고 버퍼에 저장해둔다. 
- 프로그램은 외부의 입력 데이터로부터 직접 읽는 대신, 버퍼로부터 읽음으로써 읽기 성능이 향상된다.
```java
BufferedInputStream bis = new BufferedInputStream(바이트입력스트림);
BufferedReader br = new BufferReader(문자입력스트림);
```
<hr />

### BufferedOutputStream과 BufferdWriter
- 프로그램에서 전송한 데이터를 내부 버퍼에 쌓아두었다가 버퍼가 꽉 차면, 버퍼의 모든 데이터를 한꺼번에 보낸다. 
- 프로그램은 입장에서 보면 직접 데이터를 보내는 것이 아니라, 메모리 버퍼로 데이터를 고속 전송하기 때문에 실행 성능이 향상되는 효과를 얻게 된다.
```java
BufferedOuputStream bis = new BufferedOutnputStream(바이트출력스트림);
BufferedWriter br = new BufferWriter(문자출력스트림);
```

## 👨‍🌾기본 타입 입출력 보조 스트림
> 기본 타입으로 입출력이 가능해진다.

### DataInputStream
 
```java
DataInputStream dis = new DataInputStream(바이트입력스트림);
```

#### 메소드
- read기본타입() : 바이트 단위를 읽어서 기본 타입으로 변환하여 리턴한다.

```java
FileInputStream fis = new FileInputStream("C:/Temp/primitive.dat");
DataInputStream dis = new DataInputStream(fis);

for(int i=0; i<2; i++) {
	String name = dis.readUTF();
	double score = dis.readDouble();
	int order = dis.readInt();
	System.out.println(name + " : " + score + " : " + order);
}
```
<hr />

### DataOutputSteam
```java
DataOutputStream dos = new DataOutputStream(바이트출력스트림);
```

#### 메소드
- write기본타입(변수) : 해당 타입 변수를 바이트 단위로 바꾸어 출력한다.

```java
FileOutputStream fos = new FileOutputStream("C:/Temp/primitive.dat");
DataOutputStream dos = new DataOutputStream(fos);

dos.writeUTF("홍길동");
dos.writeDouble(95.5);
dos.writeInt(1);

dos.writeUTF("감자바");
dos.writeDouble(90.3);
dos.writeInt(2);

dos.flush(); dos.close(); fos.close();
```

## 👩‍🌾프린터 보조 스트림 

### PrintWriter

```java
PrintWriter pw = new PrintWriter(문자출력스트림);
```

#### 메소드
- print(변수) : 출력할 데이터 타입에 따라 메소드 오버로딩이 되어 있다.

```java
PrintWriter pw = response.getWriter();
pw.println(json);
pw.flush();
pw.close();
```
<hr />

### PrintStream
바이트스트림

```java
PrintStream ps = new PrintStream(파일출력스트림);
```

#### 메소드
- print(변수) : 출력할 데이터 타입에 따라 메소드 오버로딩이 되어 있다.

```java
FileOutputStream fos = new FileOutputStream("C:/Temp/file.txt");
PrintStream ps = new PrintStream(fos);
ps.println("[프린터 보조 스트림]");
ps.flush();
ps.close();
fos.close();
```