/* tslint:disable */
/* eslint-disable */
/**
 * Thing-People-List Api
 * Public API of the Thing-People-List Service. Thing-People-List is a Web-Service to keep track of which people did what i.e. who borrowed what from you.
 *
 * The version of the OpenAPI document: 2.0.0
 * Contact: dev@finn-thorben.me
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from "../runtime";
/**
 *
 * @export
 * @interface CreateThingDto
 */
export interface CreateThingDto {
  /**
   *
   * @type {string}
   * @memberof CreateThingDto
   */
  name: string;
  /**
   *
   * @type {string}
   * @memberof CreateThingDto
   */
  description?: string;
  /**
   *
   * @type {string}
   * @memberof CreateThingDto
   */
  personId: string;
}

export function CreateThingDtoFromJSON(json: any): CreateThingDto {
  return CreateThingDtoFromJSONTyped(json, false);
}

export function CreateThingDtoFromJSONTyped(
  json: any,
  ignoreDiscriminator: boolean
): CreateThingDto {
  if (json === undefined || json === null) {
    return json;
  }
  return {
    name: json["name"],
    description: !exists(json, "description") ? undefined : json["description"],
    personId: json["personId"],
  };
}

export function CreateThingDtoToJSON(value?: CreateThingDto | null): any {
  if (value === undefined) {
    return undefined;
  }
  if (value === null) {
    return null;
  }
  return {
    name: value.name,
    description: value.description,
    personId: value.personId,
  };
}
