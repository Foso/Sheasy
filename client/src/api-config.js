let backendHost;

const apiVersion = 'v1';

const hostname = window && window.location && window.location.hostname;

 if(hostname === 'localhost') {
    backendHost = '192.168.178.20:8766';
  } 
else {
  backendHost = hostname+":8766";
}

export const API_ROOT = `${backendHost}`;
