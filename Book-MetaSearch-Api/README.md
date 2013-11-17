Book-MetaSearch-Api
===================

This module creates a RESTful web service which exposes data needed by the Book MetaSearch app. It has
the logic necessary to expose information from many of the data sources used by the app such as:

**Current:**

- Amazon Affiliates SOAP Api

**Planned:**

- Cached Audible inventory list
- Cached Audible top-200 list

More services will be added as they are needed

Requirements
------------

- A Unix operating system

Installation
------------

Build this code using Maven. A plugin included will allow this project to
be build on Windows and convert line endings as necessary. This service must
be run on a Unix operating system.

    maven clean package

The package process will create a tar.gz file containing all required files
and installation script.

    tar xzvf Book-MetaSearch-Api.tar.gz
    chmod +x install.sh
    ./install.sh

After finishing installation, copy the included api-keys.properties.template
and fill with your api keys.

    cd /opt/book-metasearch/api/config
    cp api-keys.properties.template api-keys.properties
    vim api-keys.properties

Uninstallation
--------------

Included in the installation directory is an uninstallation script.

**Note:** The uninstallation script will remove all files in the installation
directory including your api-keys.properties file.

    cd /opt/book-metasearch/api/bin
    ./uninstall.sh

Usage
-----

To run the application, execute the included service script

    /etc/init.d/book-metasearch-api {start|stop|restart|status}

API
---

This API documentation will be divided up based on the service exposed

### Amazon Affiliates SOAP Api

Tha Amazon Affiliates API is only exposed through a SOAP endpoint. Unfortunately,
Android has a bit of a hard time with SOAP since the core SOAP classes from Java are
not included with Android. This interface acts as a middle man, converting the SOAP
API from Amazon into a RESTful API returning JSON.

All methods from the Amazon Affiliates Api share a common response, the serialized version
of the Amazon response. Depending on the item, there may be more or fewer fields than
those shown in the example response below.

#### ASIN Search

Search for items using the Amazon ASIN number.

    GET http://<hostname>:<port>/search?method=asin&q=<asin>

#### ISBN Search

Search for items using the ISBN number.

    GET http://<hostname>:<port>/search?method=isbn&q=<isbn>

#### Example Response

    [
        {
            asin: "B0010SKUYM",
            detailPageURL: "http://www.amazon.com/The-Name-Wind-Kingkiller-Chronicle-ebook...",
            itemLinks: {
                itemLink: [
                    {
                        description: "Technical Details",
                        url: "http://www.amazon.com/The-Name-Wind-Kingkiller-Chronicle-ebook/..."
                    },
                    {
                        description: "Add To Baby Registry",
                        url: "http://www.amazon.com/gp/registry/baby/add-item.html..."
                    },
                    {
                        description: "Add To Wedding Registry",
                        url: "http://www.amazon.com/gp/registry/wedding/add-item.html..."
                    },
                    {
                        description: "Add To Wishlist",
                        url: "http://www.amazon.com/gp/registry/wishlist/add-item.html..."
                    },
                    {
                        description: "Tell A Friend",
                        url: "http://www.amazon.com/gp/pdp/taf/B0010SKUYM%3FSubscriptionId..."
                    },
                    {
                        description: "All Customer Reviews",
                        url: "http://www.amazon.com/review/product/B0010SKUYM..."
                    },
                    {
                        description: "All Offers",
                        url: "http://www.amazon.com/gp/offer-listing/B0010SKUYM..."
                    }
                ]
            },
            itemAttributes: {
                author: [ "Patrick Rothfuss" ],
                manufacturer: "DAW",
                productGroup: "eBooks",
                title: "The Name of the Wind: The Kingkiller Chronicle: Day One"
            }
        }
    ]