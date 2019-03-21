package com.iremote.test.mock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.IWriteCompletionHandler;

import com.iremote.infraredtrans.Remoter;
import com.iremote.test.data.ConstDefine;

public class MockConnection implements INonBlockingConnection {

	private Remoter r = new Remoter();
	private byte[] data ;
	
	public MockConnection(Remoter r , byte[] data) 
	{
		this.r = r ;
		this.data = data ;
	}
	
	public MockConnection() 
	{
		r.setHaslogin(true);
		r.setUuid(ConstDefine.DEVICE_ID);
	}
	
	public MockConnection(Remoter r) 
	{
		this.r = r ;
	}
	
	@Override
	public Object getAttachment() {
		return r;
	}

	@Override
	public long getConnectionTimeoutMillis() {
		return 0;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public long getIdleTimeoutMillis() {
		return 0;
	}

	@Override
	public InetAddress getLocalAddress() {
		return null;
	}

	@Override
	public int getLocalPort() {
		return 0;
	}

	@Override
	public Object getOption(String arg0) throws IOException {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Class> getOptions() {
		return null;
	}

	@Override
	public long getRemainingMillisToConnectionTimeout() {
		return 0;
	}

	@Override
	public long getRemainingMillisToIdleTimeout() {
		return 0;
	}

	@Override
	public InetAddress getRemoteAddress() {
		return null;
	}

	@Override
	public int getRemotePort() {
		return 0;
	}

	@Override
	public boolean isServerSide() {
		return false;
	}

	@Override
	public void setAttachment(Object arg0) {

	}

	@Override
	public void setConnectionTimeoutMillis(long arg0) {

	}

	@Override
	public void setIdleTimeoutMillis(long arg0) {

	}

	@Override
	public void setOption(String arg0, Object arg1) throws IOException {

	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public int read(ByteBuffer arg0) throws IOException {
		arg0.put(this.data);
		return this.data.length;
	}

	@Override
	public byte readByte() throws IOException {
		return 0;
	}

	@Override
	public ByteBuffer[] readByteBufferByDelimiter(String arg0) throws IOException {
		return null;
	}

	@Override
	public ByteBuffer[] readByteBufferByDelimiter(String arg0, int arg1)
			throws IOException, MaxReadSizeExceededException {
		return null;
	}

	@Override
	public ByteBuffer[] readByteBufferByLength(int arg0) throws IOException {
		return null;
	}

	@Override
	public byte[] readBytesByDelimiter(String arg0) throws IOException {
		return null;
	}

	@Override
	public byte[] readBytesByDelimiter(String arg0, int arg1) throws IOException, MaxReadSizeExceededException {
		return null;
	}

	@Override
	public byte[] readBytesByLength(int arg0) throws IOException {
		return null;
	}

	@Override
	public double readDouble() throws IOException {
		return 0;
	}

	@Override
	public int readInt() throws IOException {
		return 0;
	}

	@Override
	public long readLong() throws IOException {
		return 0;
	}

	@Override
	public short readShort() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readStringByDelimiter(String arg0) throws IOException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readStringByDelimiter(String arg0, int arg1)
			throws IOException, UnsupportedEncodingException, MaxReadSizeExceededException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readStringByLength(int arg0) throws IOException, BufferUnderflowException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long transferTo(WritableByteChannel arg0, int arg1) throws IOException, ClosedChannelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long transferFrom(ReadableByteChannel arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long transferFrom(ReadableByteChannel arg0, int arg1) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(byte arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(byte... arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(ByteBuffer arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long write(ByteBuffer[] arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long write(List<ByteBuffer> arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(int arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(short arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(long arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(double arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(String arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(byte[] arg0, int arg1, int arg2) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long write(ByteBuffer[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void activateSecuredMode() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int available() throws IOException {
		// TODO Auto-generated method stub
		return this.data.length;
	}

	@Override
	public void deactivateSecuredMode() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() throws ClosedChannelException, IOException, SocketTimeoutException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlushMode getFlushmode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IHandler getHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxReadBufferThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPendingWriteDataSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getReadBufferVersion() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Executor getWorkerpool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWriteTransferRate() throws ClosedChannelException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int indexOf(String arg0) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int indexOf(String arg0, String arg1) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoflush() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReceivingSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecuredModeActivateable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markReadPosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public void markWritePosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public ByteBuffer[] readByteBufferByDelimiter(String arg0, String arg1)
			throws IOException, BufferUnderflowException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteBuffer[] readByteBufferByDelimiter(String arg0, String arg1, int arg2)
			throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] readBytesByDelimiter(String arg0, String arg1) throws IOException, BufferUnderflowException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] readBytesByDelimiter(String arg0, String arg1, int arg2)
			throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readStringByDelimiter(String arg0, String arg1)
			throws IOException, BufferUnderflowException, UnsupportedEncodingException, MaxReadSizeExceededException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readStringByDelimiter(String arg0, String arg1, int arg2)
			throws IOException, BufferUnderflowException, UnsupportedEncodingException, MaxReadSizeExceededException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readStringByLength(int arg0, String arg1)
			throws IOException, BufferUnderflowException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeReadMark() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeWriteMark() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean resetToReadMark() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetToWriteMark() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resumeReceiving() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAutoflush(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEncoding(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlushmode(FlushMode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHandler(IHandler arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxReadBufferThreshold(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWorkerpool(Executor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWriteTransferRate(int arg0) throws ClosedChannelException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspendReceiving() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public long transferFrom(FileChannel arg0) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void unread(ByteBuffer[] arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unread(ByteBuffer arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unread(byte[] arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unread(String arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int write(String arg0, String arg1) throws IOException, BufferOverflowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void write(ByteBuffer[] arg0, IWriteCompletionHandler arg1) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(ByteBuffer arg0, IWriteCompletionHandler arg1) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(List<ByteBuffer> arg0, IWriteCompletionHandler arg1) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(byte[] arg0, IWriteCompletionHandler arg1) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(String arg0, String arg1, IWriteCompletionHandler arg2) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(ByteBuffer[] arg0, int arg1, int arg2, IWriteCompletionHandler arg3) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2, IWriteCompletionHandler arg3) throws IOException {
		// TODO Auto-generated method stub

	}

}
