package unimelb.bitbox;

import java.util.ArrayList;

import unimelb.bitbox.util.Configuration;
import unimelb.bitbox.util.Document;
import unimelb.bitbox.util.FileSystemManager.FileSystemEvent;
import unimelb.bitbox.util.HostPort;

public class SystemEventMessage {
	
	public Document invalidProtocol() {
		Document info = new Document();
		info.append("command", "INVALID_PROTOCOL");
		info.append("message", "message must contain a command field as string");
		return info;
	}
	public Document connectionRefused(ArrayList<Document> peerList) {
		Document info = new Document();
		info.append("command", "CONNECTION_REFUSED");
		info.append("message", "connection limit reached");
		info.append("peers", Configuration.getConfigurationValue("peers"));		
		return info;
	}
	
	public Document HandShakeRequest(String ipAddress, int port) {
		Document info = new Document();
		HostPort docHostPort = new HostPort(ipAddress, port);
		info.append("command", "HANDSHAKE_REQUEST");
		info.append("hostPort", docHostPort.toDoc());
		return info;
	}
	public Document HandShakeResponse() {
		Document info = new Document();
		HostPort serverHostPort = new HostPort(Configuration.getConfigurationValue("advertisedName"),Integer.parseInt(Configuration.getConfigurationValue("port")));
		info.append("command", "HANDSHAKE_RESPONSE");
		info.append("hostPort", serverHostPort.toDoc());
		return info;
	}
	public Document fileCreateRequest(Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_CREATE_REQUEST");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		return info;
	}
	
	public Document fileCreateResponseSuccess (Document info) {
		Document returnInfo = new Document();
		Document fileDescriptorDoc = new Document();
		fileDescriptorDoc = (Document) info.get("fileDescriptor");
		returnInfo.append("command", "FILE_CREATE_RESPONSE");
		returnInfo.append("fileDescriptor", fileDescriptorDoc);
		returnInfo.append("pathName", info.getString("pathName"));
		returnInfo.append("message", "file loader ready");
		returnInfo.append("status", true);
		return returnInfo;
	}
	
	public Document fileCreateResponseRefuseFail (Document info) {
		Document returnInfo = new Document();
		Document fileDescriptorDoc = new Document();
		fileDescriptorDoc = (Document) info.get("fileDescriptor");
		returnInfo.append("command", "FILE_CREATE_RESPONSE");
		returnInfo.append("fileDescriptor", fileDescriptorDoc);
		returnInfo.append("pathName", info.getString("pathName"));
		returnInfo.append("message", "There is a problem loading a file");
		returnInfo.append("status", false);
		return returnInfo;
	}
	
	public Document fileCreateResponseRefuseExist (Document info) {
		Document returnInfo = new Document();
		Document fileDescriptorDoc = new Document();
		fileDescriptorDoc = (Document) info.get("fileDescriptor");
		returnInfo.append("command", "FILE_CREATE_RESPONSE");
		returnInfo.append("fileDescriptor", fileDescriptorDoc);
		returnInfo.append("pathName", info.getString("pathName"));
		returnInfo.append("message", "pathname already exists");
		returnInfo.append("status", false);
		return returnInfo;
	}
	public Document fileCreateResponseRefuse (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_CREATE_RESPONSE");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		info.append("message", "unsafe pathname given");
		info.append("status", false);
		return info;
	}
	
	public Document fileBytesRequest (Document info) {
		if(!info.containsKey("position")) {
			Document fileDescriptorDoc = new Document();
			fileDescriptorDoc = (Document) info.get("fileDescriptor");
			info.append("command", "FILE_BYTES_REQUEST");
			info.append("fileDescriptor", fileDescriptorDoc);
			info.append("pathName", info.getString("pathName"));
			info.append("position", 0);
			info.append("length", fileDescriptorDoc.getLong("fileSize"));
			return info;
		}else {
			Document fileDescriptorDoc = new Document();
			fileDescriptorDoc = (Document) info.get("fileDescriptor");
			info.append("command", "FILE_BYTES_REQUEST");
			info.append("fileDescriptor", fileDescriptorDoc);
			info.append("pathName", info.getString("pathName"));
			info.append("length", fileDescriptorDoc.getLong("fileSize"));
			return info;
		}

	}
	
	public Document fileBytesResponse (Document info,String base64EncodeInfo) {
		Document fileDescriptorDoc = new Document();
		fileDescriptorDoc = (Document) info.get("fileDescriptor");
		info.append("command", "FILE_BYTES_RESPONSE");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", info.getString("pathName"));
		info.append("length", fileDescriptorDoc.getLong("fileSize"));
		info.append("content", base64EncodeInfo);
		info.append("message", "successful read");
		info.append("status", true);
		return info;
	}
	
	public Document fileDeleteRequest (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_DELETE_REQUEST");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		return info;
	}
	
	public Document fileDeleteResponseSuccess (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_DELETE_RESPONSE");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		info.append("message", "file deleted");
		info.append("status", true);
		return info;
	}
	
	public Document fileDeleteResponseNotExist (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_DELETE_RESPONSE");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		info.append("message", "there was a problem deleting the file");
		info.append("status", false);
		return info;
	}
	
	public Document fileModifyRequest (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_MODIFY_REQUEST");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		return info;
	}
	
	public Document fileModifyReponseSuccess (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_MODIFY_RESPONSE");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		info.append("message", "file loader ready");
		info.append("status", true);
		return info;
	}
	
	public Document fileModifyReponseFail (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_MODIFY_RESPONSE");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		info.append("message", "there was a problem modifying the file");
		info.append("status", false);
		return info;
	}

	public Document fileModifyReponseUnsafePathname (Document fileDescriptorDoc, String fileName) {
		Document info = new Document();
		info.append("command", "FILE_MODIFY_RESPONSE");
		info.append("fileDescriptor", fileDescriptorDoc);
		info.append("pathName", fileName);
		info.append("message", "unsafe pathname given");
		info.append("status", false);
		return info;
	}
	
	public Document directoryCreateRequest (FileSystemEvent fileSystemEvent) {
		Document returnInfo = new Document();
		returnInfo.append("command", "DIRECTORY_CREATE_REQUEST");
		returnInfo.append("pathName", fileSystemEvent.name);
		return returnInfo;
	}
	
	public Document directoryCreateReponseSuccess (Document info) {
		Document returnInfo = new Document();
		returnInfo.append("command", "DIRECTORY_CREATE_RESPONSE");
		returnInfo.append("pathName", info.getString("pathName"));
		returnInfo.append("message", "directory created");
		returnInfo.append("status", true);
		return returnInfo;
	}
	
	public Document directoryCreateReponseFail (Document info) {
		Document returnInfo = new Document();
		returnInfo.append("command", "DIRECTORY_CREATE_RESPONSE");
		returnInfo.append("pathName", info.getString("pathName"));
		returnInfo.append("message", "there was a problem creating the directory");
		returnInfo.append("status", false);
		return returnInfo;
	}
	
	public Document directoryDeleteRequest (FileSystemEvent fileSystemEvent) {
		Document returnInfo = new Document();
		returnInfo.append("command", "DIRECTORY_DELETE_REQUEST");
		returnInfo.append("pathName", fileSystemEvent.name);
		return returnInfo;
	}
	
	public Document directoryDeleteReponseSuccess (Document info) {
		Document returnInfo = new Document();
		returnInfo.append("command", "DIRECTORY_DELETE_RESPONSE");
		returnInfo.append("pathName", info.getString("pathName"));
		returnInfo.append("message", "directory deleted");
		returnInfo.append("status", true);
		return returnInfo;
	}
	
	public Document directoryDeleteReponseFail (Document info) {
		Document returnInfo = new Document();
		returnInfo.append("command", "DIRECTORY_DELETE_RESPONSE");
		returnInfo.append("pathName", info.getString("pathName"));
		returnInfo.append("message", "there was a problem deleting the directory");
		returnInfo.append("status", false);
		return returnInfo;
	}

}
