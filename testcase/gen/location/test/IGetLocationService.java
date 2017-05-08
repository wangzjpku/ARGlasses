/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\test\\src\\location\\test\\IGetLocationService.aidl
 */
package location.test;
public interface IGetLocationService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements location.test.IGetLocationService
{
private static final java.lang.String DESCRIPTOR = "location.test.IGetLocationService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an location.test.IGetLocationService interface,
 * generating a proxy if needed.
 */
public static location.test.IGetLocationService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof location.test.IGetLocationService))) {
return ((location.test.IGetLocationService)iin);
}
return new location.test.IGetLocationService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getLongitude:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getLongitude();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_getLatitude:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getLatitude();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_getDistance:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getDistance();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_getAverageSpeed:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getAverageSpeed();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_countSteps:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.countSteps();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDegree:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getDegree();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
case TRANSACTION_startDeadReckoningService:
{
data.enforceInterface(DESCRIPTOR);
this.startDeadReckoningService();
reply.writeNoException();
return true;
}
case TRANSACTION_getGPSStatus:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getGPSStatus();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSensorStatus:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getSensorStatus();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_changeGPSStatus:
{
data.enforceInterface(DESCRIPTOR);
this.changeGPSStatus();
reply.writeNoException();
return true;
}
case TRANSACTION_changeSensorStatus:
{
data.enforceInterface(DESCRIPTOR);
this.changeSensorStatus();
reply.writeNoException();
return true;
}
case TRANSACTION_unRegisterSensors:
{
data.enforceInterface(DESCRIPTOR);
this.unRegisterSensors();
reply.writeNoException();
return true;
}
case TRANSACTION_unRegisterGPS:
{
data.enforceInterface(DESCRIPTOR);
this.unRegisterGPS();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements location.test.IGetLocationService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public double getLongitude() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLongitude, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public double getLatitude() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLatitude, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public double getDistance() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDistance, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public double getAverageSpeed() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAverageSpeed, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int countSteps() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_countSteps, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public double getDegree() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDegree, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void startDeadReckoningService() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startDeadReckoningService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean getGPSStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getGPSStatus, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean getSensorStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSensorStatus, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void changeGPSStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_changeGPSStatus, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void changeSensorStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_changeSensorStatus, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void unRegisterSensors() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unRegisterSensors, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void unRegisterGPS() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unRegisterGPS, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getLongitude = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getLatitude = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getDistance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getAverageSpeed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_countSteps = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getDegree = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_startDeadReckoningService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getGPSStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getSensorStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_changeGPSStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_changeSensorStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_unRegisterSensors = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_unRegisterGPS = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
public double getLongitude() throws android.os.RemoteException;
public double getLatitude() throws android.os.RemoteException;
public double getDistance() throws android.os.RemoteException;
public double getAverageSpeed() throws android.os.RemoteException;
public int countSteps() throws android.os.RemoteException;
public double getDegree() throws android.os.RemoteException;
public void startDeadReckoningService() throws android.os.RemoteException;
public boolean getGPSStatus() throws android.os.RemoteException;
public boolean getSensorStatus() throws android.os.RemoteException;
public void changeGPSStatus() throws android.os.RemoteException;
public void changeSensorStatus() throws android.os.RemoteException;
public void unRegisterSensors() throws android.os.RemoteException;
public void unRegisterGPS() throws android.os.RemoteException;
}
