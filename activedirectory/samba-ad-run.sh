#!/bin/bash
 
set -e
 
[ -f /var/lib/samba/.setup ] || {
    >&2 echo "[ERROR] Samba is not setup yet, which should happen automatically. Look for errors!"
    exit 127
}

# Add user camunda
samba-tool user create camunda camunda123! \
	--uid=camunda --uid-number=10001 --gid-number=100 \
	--unix-home=/home/camunda --home-directory=/home/camunda \
	--login-shell=/bin/bash \
	--gecos='Camunda Admin' --given-name=Camunda --surname=Admin

# Add camunda user to the Admin group
samba-tool group addmembers Administrators camunda

samba -i -s /var/lib/samba/private/smb.conf
