#Marvin的blog
[Pelican](http://docs.getpelican.com/en/3.6.0/quickstart.html)
Install
---

        $ pip install pelican markdown


Create a project
---

        $ mkdir blog & cd


Generate site
---

        $ pelican-quickstart
Write something in file.md in content folder

        $ pelican content
Preview site
---
        
        $ cd output
        $ python -m pelican.server
publish        
---

        $ pip install ghp-import
        $ make publish
        $ ghp-import -b master output
        $ git push master

