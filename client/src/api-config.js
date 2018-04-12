let backendHost;
const apiVersion = 'v1';

const hostname = window && window.location && window.location.hostname;

 if(hostname === 'localhost') {
    backendHost = '192.168.178.21:8765';
  } 
else {
  backendHost = hostname+":8765";
}

export const API_ROOT = `${backendHost}`;
